package org.proiect.gateway_3.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.netty.buffer.PooledByteBufAllocator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.proiect.gateway_3.proto.AuthServiceGrpc;
import org.proiect.gateway_3.proto.AuthServiceOuterClass;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.Serializable;
import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.Objects;
import java.util.function.Function;

import static io.grpc.netty.shaded.io.grpc.netty.NegotiationType.TLS;
import static org.springframework.cloud.gateway.support.GatewayToStringStyler.filterToStringCreator;

@Component
public class JSONToGRPCFilterFactory extends AbstractGatewayFilterFactory<Object> {

    @Override
    public GatewayFilter apply(Object config) {
        GatewayFilter filter = new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                GRPCResponseDecorator modifiedResponse = new GRPCResponseDecorator(exchange);

                return modifiedResponse
                        .writeWith(exchange.getRequest().getBody())
                        .then(chain.filter(exchange.mutate()
                                .response(modifiedResponse).build()));
            }

            @Override
            public String toString() {
                return filterToStringCreator(
                        JSONToGRPCFilterFactory.this)
                        .toString();
            }
        };

        int order = NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1;
        return new OrderedGatewayFilter(filter, order);
    }

    @Override
    public String name() {
        return "JSONToGRPCFilter";
    }

    static class GRPCResponseDecorator extends ServerHttpResponseDecorator {

        private final ServerWebExchange exchange;

        public GRPCResponseDecorator(ServerWebExchange exchange) {
            super(exchange.getResponse());
            this.exchange = exchange;
        }

        @Override
        public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
            exchange.getResponse().getHeaders().set("Content-Type", "application/json");

            URI requestURI = exchange.getRequest().getURI();
            String path = exchange.getRequest().getURI().getPath();

            ManagedChannel channel = ManagedChannelBuilder.forAddress(requestURI.getHost(), 50051)
                    .usePlaintext()
                    .build();

            if (path.startsWith("/grpc/auth"))
                return getDelegate().writeWith(deserializeJSONRequest(AuthServiceOuterClass.AuthRequest.class)
                        .map(jsonRequest -> {
                            String username = jsonRequest.getRequest().getUsername();
                            String password = jsonRequest.getRequest().getPassword();
                            return AuthServiceGrpc.newBlockingStub(channel)
                                    .auth(AuthServiceOuterClass.AuthRequest.newBuilder()
                                            .setUsername(username)
                                            .setPassword(password)
                                            .build());
                        })
                        .map(this::serialiseJSONResponse)
                        .map(wrapGRPCResponse())
                        .cast(DataBuffer.class)
                        .last());
            else if (path.startsWith("/grpc/register"))
                return getDelegate().writeWith(deserializeJSONRequest(AuthServiceOuterClass.RegisterRequest.class)
                        .map(jsonRequest -> {
                            String username = jsonRequest.getRequest().getUsername();
                            String password = jsonRequest.getRequest().getPassword();
                            String role = jsonRequest.getRequest().getRole();
                            return AuthServiceGrpc.newBlockingStub(channel)
                                    .register(AuthServiceOuterClass.RegisterRequest.newBuilder()
                                            .setUsername(username)
                                            .setPassword(password)
                                            .setRole(role)
                                            .build());
                        })
                        .map(this::serialiseJSONResponse)
                        .map(wrapGRPCResponse())
                        .cast(DataBuffer.class)
                        .last());
            return Mono.error(new IllegalArgumentException("Unsupported URI path"));
        }

        private <T> Flux<Req<T>> deserializeJSONRequest(Class<T> responseType) {
            return exchange.getRequest()
                    .getBody()
                    .mapNotNull(dataBufferBody -> {
                        ResolvableType targetType = ResolvableType.forClassWithGenerics(Req.class, responseType);
                        return new Jackson2JsonDecoder()
                                .decode(dataBufferBody, targetType, null, null);
                    })
                    .cast(Req.class)
                    .map(req -> {
                        Req<T> typedReq = new Req<>();
                        typedReq.setRequest((T) req.getRequest());
                        return typedReq;
                    });
        }

        private <T> Resp<T> serialiseJSONResponse(T gRPCResponse) {
            return new Resp<>(gRPCResponse);
        }

        private <T> Function<Resp<T>, DataBuffer> wrapGRPCResponse() {
            return jsonResponse -> {
                try {
                    return new NettyDataBufferFactory(new PooledByteBufAllocator())
                            .wrap(Objects.requireNonNull(new ObjectMapper()
                                    .writeValueAsBytes(jsonResponse)));
                } catch (JsonProcessingException e) {
                    return new NettyDataBufferFactory(new PooledByteBufAllocator())
                            .allocateBuffer();
                }
            };
        }

    }

    @Setter
    @Getter
    static class Req<T> {

        private T request;

    }

    @Setter
    @Getter
    static class Resp<T> implements Serializable {
        private static final long serialVersionUID = 1L;

        private T response;

        public Resp() {
        }

        public Resp(T response) {
            this.response = response;
        }

    }

}