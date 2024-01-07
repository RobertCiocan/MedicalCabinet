package org.proiect.gateway_ex.filter.internal;

import org.proiect.gateway_ex.proto.AuthServiceGrpc;
import org.proiect.gateway_ex.proto.AuthServiceOuterClass;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;


import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Configuration
public class JwtValidationFilter extends AbstractGatewayFilterFactory<Object> {

    private final AuthServiceGrpc.AuthServiceBlockingStub authServiceBlockingStub;

    public JwtValidationFilter() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        this.authServiceBlockingStub = AuthServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            return validateJwtWithIdm(token)
                    .flatMap(isValid -> {
                        if (isValid) {
                            return chain.filter(exchange);
                        } else {
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            return exchange.getResponse().setComplete();
                        }
                    });
        };
    }

    private Mono<Boolean> validateJwtWithIdm(String token) {
        AuthServiceOuterClass.ValidateRequest request = AuthServiceOuterClass.ValidateRequest.newBuilder().setToken(token).build();
        AuthServiceOuterClass.ValidateResponse response = authServiceBlockingStub.validate(request);

        return Mono.just(response.getIsValid());
    }
}

