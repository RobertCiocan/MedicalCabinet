package org.proiect.gateway_3.filter;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.proiect.gateway_3.proto.AuthServiceGrpc;
import org.proiect.gateway_3.proto.AuthServiceOuterClass;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
public class LoginFilter extends AbstractGatewayFilterFactory<Object> {

    private final AuthServiceGrpc.AuthServiceBlockingStub authServiceBlockingStub;

    private final List<String> paths = List.of(
            "/login"
    );

    public LoginFilter() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        this.authServiceBlockingStub = AuthServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            String requestedPath = exchange.getRequest().getPath().value();
            if (!paths.contains(requestedPath)) {
                return chain.filter(exchange);
            }
            System.out.println(exchange.getRequest().getQueryParams().getFirst("username"));
            return login(
                    exchange.getRequest().getQueryParams().getFirst("username"),
                    exchange.getRequest().getQueryParams().getFirst("password"))
                    .flatMap(token -> {
                        if (token != null) {
                            exchange.getResponse().getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
                            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(token.getBytes());
                            return exchange.getResponse().writeWith(Mono.just(buffer));
                        } else {
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            return exchange.getResponse().setComplete();
                        }
                    })
                    .onErrorResume(throwable -> {
                        String errorMessage;
                        if (throwable instanceof StatusRuntimeException grpcError) {
                            errorMessage = grpcError.getStatus().getDescription();
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        } else {
                            errorMessage = "Internal server error";
                            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                        }

                        exchange.getResponse().getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
                        assert errorMessage != null;
                        exchange.getResponse().getHeaders().setContentLength(errorMessage.length());
                        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(errorMessage.getBytes());
                        return exchange.getResponse().writeWith(Mono.just(buffer));
                    });
        };
    }

    private Mono<String> login(String username, String password) {
        return Mono.create(sink -> {
            try {
                AuthServiceOuterClass.AuthRequest request =
                        AuthServiceOuterClass.AuthRequest.newBuilder()
                                .setUsername(username)
                                .setPassword(password)
                                .build();
                AuthServiceOuterClass.AuthResponse response = authServiceBlockingStub.auth(request);
                sink.success(response.getToken());
            } catch (Exception e) {
                sink.error(e);
            }
        });
    }
}

