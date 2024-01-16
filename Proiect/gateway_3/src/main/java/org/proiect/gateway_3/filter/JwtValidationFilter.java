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

import java.util.Arrays;
import java.util.List;

@Configuration
public class JwtValidationFilter extends AbstractGatewayFilterFactory<Object> {

    private final AuthServiceGrpc.AuthServiceBlockingStub authServiceBlockingStub;

    private final List<String> excludedPaths = Arrays.asList(
            "/login",
            "/register"
    );

    public JwtValidationFilter() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        this.authServiceBlockingStub = AuthServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            String requestedPath = exchange.getRequest().getPath().value();
            if (excludedPaths.contains(requestedPath)) {
                return chain.filter(exchange);
            }

            return validateJwtWithIdm(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                    .flatMap(isValid -> {
                        System.out.println(isValid);
                        if (isValid) {
                            return chain.filter(exchange);
                        } else {
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            return exchange.getResponse().setComplete();
                        }
                    })
                    .onErrorResume(throwable -> {
                        String errorMessage;
                        if (throwable instanceof StatusRuntimeException grpcError) {
                            if (grpcError.getStatus().getCode() == Status.Code.UNAUTHENTICATED) {
                                errorMessage = grpcError.getStatus().getDescription();
                                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            } else {
                                errorMessage = grpcError.getStatus().getDescription();
                                exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                            }
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

    private Mono<Boolean> validateJwtWithIdm(String token) {
        return Mono.create(sink -> {
            try {
                AuthServiceOuterClass.ValidateRequest request =
                        AuthServiceOuterClass.ValidateRequest.newBuilder().setToken(token).build();
                AuthServiceOuterClass.ValidateResponse response = authServiceBlockingStub.validate(request);
                sink.success(response.getIsValid());
            } catch (Exception e) {
                sink.error(e);
            }
        });
    }
}

