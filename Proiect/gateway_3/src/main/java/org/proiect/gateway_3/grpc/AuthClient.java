package org.proiect.gateway_3.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.proiect.gateway_3.proto.AuthServiceGrpc;
import org.proiect.gateway_3.proto.AuthServiceOuterClass;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;


public class AuthClient {
    private final ManagedChannel channel;
    private final AuthServiceGrpc.AuthServiceBlockingStub blockingStub;

    public AuthClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext());
    }

    private AuthClient(ManagedChannelBuilder<?> channelBuilder) {
        channel = channelBuilder.build();
        blockingStub = AuthServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public AuthServiceOuterClass.AuthResponse authenticate(AuthServiceOuterClass.AuthRequest request) {
        try {
            return blockingStub.auth(request);
        } catch (StatusRuntimeException e) {
            return AuthServiceOuterClass.AuthResponse.newBuilder()
                    .setToken("")
                    .setErrorMessage(e.getMessage())
                    .build();
        }
    }

    public AuthServiceOuterClass.LogoutResponse logout(AuthServiceOuterClass.LogoutRequest request) {
        try {
            return blockingStub.logout(request);
        } catch (StatusRuntimeException e) {
            return AuthServiceOuterClass.LogoutResponse.newBuilder()
                    .setSuccess(false)
                    .build();
        }
    }

    public AuthServiceOuterClass.ValidateResponse validate(AuthServiceOuterClass.ValidateRequest request) {
        try {
            return blockingStub.validate(request);
        } catch (StatusRuntimeException e) {
            return AuthServiceOuterClass.ValidateResponse.newBuilder()
                    .setIsValid(false)
                    .build();
        }
    }

    public AuthServiceOuterClass.RegisterResponse register(AuthServiceOuterClass.RegisterRequest request) {
        try {
            return blockingStub.register(request);
        } catch (StatusRuntimeException e) {
            return AuthServiceOuterClass.RegisterResponse.newBuilder()
                    .setUsername("")
                    .setRole("")
                    .setUid(0)
                    .build();
        }
    }

    public AuthServiceOuterClass.UpdatePasswordResponse updatePassword(AuthServiceOuterClass.UpdatePasswordRequest request) {
        try {
            return blockingStub.updatePassword(request);
        } catch (StatusRuntimeException e) {
            return AuthServiceOuterClass.UpdatePasswordResponse.newBuilder()
                    .setSuccess(false)
                    .build();
        }
    }

    public AuthServiceOuterClass.BlacklisResponse setBlacklist(AuthServiceOuterClass.BlacklistRequest request) {
        try {
            return blockingStub.setBlacklist(request);
        } catch (StatusRuntimeException e) {
            return AuthServiceOuterClass.BlacklisResponse.newBuilder()
                    .setSuccess(false)
                    .build();
        }
    }

    public static void main(String[] args) {
        AuthClient client = new AuthClient("localhost", 50051);
        try {
            AuthServiceOuterClass.AuthRequest request = AuthServiceOuterClass.AuthRequest.newBuilder()
                    .setUsername("ciooocan")
                    .setPassword("parola")
                    .build();
            AuthServiceOuterClass.AuthResponse resp = client.authenticate(request);
            System.out.println(resp.getToken());
        } finally {
            try {
                client.shutdown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
