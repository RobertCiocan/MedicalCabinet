package org.proiect.gateway_3.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.proiect.gateway_3.grpc.AuthClient;
import org.proiect.gateway_3.proto.AuthServiceOuterClass;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class GRPcCallsFilter extends AbstractGatewayFilterFactory<Object> {

    private final AuthClient authClient;

    public GRPcCallsFilter() {
        this.authClient = new AuthClient("localhost", 50051);
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();
            System.out.println(path);

            if (path.startsWith("/api/")) {
                return handleGrpcRequest(exchange, chain);
            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> handleGrpcRequest(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println(exchange.getRequest().getURI().getPath());
        String functionName = extractFunctionName(exchange.getRequest().getURI().getPath());
        System.out.println(functionName);

        if (functionName != null) {
            return switch (functionName) {
                case "auth" -> exchange.getRequest().getBody()
                        .collectList()
                        .flatMap(body -> {
                            String requestBody = body.stream()
                                    .map(dataBuffer -> {
                                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(bytes);
                                        DataBufferUtils.release(dataBuffer);
                                        return bytes;
                                    })
                                    .reduce((a, b) -> {
                                        byte[] result = new byte[a.length + b.length];
                                        System.arraycopy(a, 0, result, 0, a.length);
                                        System.arraycopy(b, 0, result, a.length, b.length);
                                        return result;
                                    })
                                    .map(String::new)
                                    .orElse("");
                            String username = extractFieldValue(requestBody, "username");
                            String password = extractFieldValue(requestBody, "password");

                            AuthServiceOuterClass.AuthResponse response = authClient.authenticate(
                                    AuthServiceOuterClass.AuthRequest.newBuilder()
                                            .setUsername(username)
                                            .setPassword(password)
                                            .build());


                            if (response.getToken().isEmpty()) {
                                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                return exchange.getResponse().setComplete();
                            }

                            System.out.println(response.getToken());
                            String customJson = "{\"jwt\": \"" + response.getToken() + "\"}";

                            byte[] jsonBytes = customJson.getBytes();

                            DataBuffer dataBuffer = new DefaultDataBufferFactory().wrap(jsonBytes);

                            exchange.getResponse().setStatusCode(HttpStatus.OK);
                            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                            return exchange.getResponse().writeWith(Mono.just(dataBuffer));
                        });
                case "logout" -> logoutUser(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                        .flatMap(success -> {
                            System.out.println(success);
                            if (success) {
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
                case "register" -> exchange.getRequest().getBody()
                        .collectList()
                        .flatMap(body -> {
                            String requestBody = body.stream()
                                    .map(dataBuffer -> {
                                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(bytes);
                                        DataBufferUtils.release(dataBuffer);
                                        return bytes;
                                    })
                                    .reduce((a, b) -> {
                                        byte[] result = new byte[a.length + b.length];
                                        System.arraycopy(a, 0, result, 0, a.length);
                                        System.arraycopy(b, 0, result, a.length, b.length);
                                        return result;
                                    })
                                    .map(String::new)
                                    .orElse("");
                            String username = extractFieldValue(requestBody, "username");
                            String password = extractFieldValue(requestBody, "password");
                            String role = extractFieldValue(requestBody, "role");

                            AuthServiceOuterClass.RegisterResponse response = authClient.register(
                                    AuthServiceOuterClass.RegisterRequest.newBuilder()
                                            .setUsername(username)
                                            .setPassword(password)
                                            .setRole(role)
                                            .build());


                            System.out.println(response.getUid());
                            String customJson = "{\"uid\": \"" + response.getUid() + "\"}";

                            byte[] jsonBytes = customJson.getBytes();

                            DataBuffer dataBuffer = new DefaultDataBufferFactory().wrap(jsonBytes);

                            exchange.getResponse().setStatusCode(HttpStatus.OK);
                            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                            return exchange.getResponse().writeWith(Mono.just(dataBuffer));
                        });
                default -> {
                    exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
                    yield exchange.getResponse().setComplete();
                }
            };
        }


        return exchange.getResponse().setComplete();
    }

    private String extractFieldValue(String body, String fieldName) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(body);

            if (jsonNode.has(fieldName)) {
                return jsonNode.get(fieldName).asText();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String extractFunctionName(String path) {
        System.out.println(path);
        int grpcIndex = path.indexOf("/api/");
        if (grpcIndex != -1) {
            return path.substring(grpcIndex + 5);
        }
        return null;
    }

    private Mono<Boolean> logoutUser(String token) {
        return Mono.create(sink -> {
            try {
                AuthServiceOuterClass.LogoutRequest request =
                        AuthServiceOuterClass.LogoutRequest.newBuilder().setToken(token).build();
                AuthServiceOuterClass.LogoutResponse response = authClient.logout(request);
                sink.success(response.getSuccess());
            } catch (Exception e) {
                sink.error(e);
            }
        });
    }

}
