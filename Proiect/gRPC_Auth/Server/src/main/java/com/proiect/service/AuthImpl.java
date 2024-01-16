package com.proiect.service;


import com.proiect.proto.AuthServiceGrpc;
import com.proiect.proto.AuthServiceOuterClass;
import com.proiect.repository.UserRepository;
import com.proiect.utils.JwtUtil;
import com.proiect.utils.RedisUtil;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import com.proiect.model.User;
import org.hibernate.SessionFactory;

public class AuthImpl extends AuthServiceGrpc.AuthServiceImplBase {
    private final UserRepository userRepository;

    public AuthImpl(SessionFactory sessionFactory) {
        this.userRepository = new UserRepository(sessionFactory);
    }

    @Override
    public void auth(AuthServiceOuterClass.AuthRequest request,
                     StreamObserver<AuthServiceOuterClass.AuthResponse> responseObserver) {
        System.out.println("Received request for authentication");
        try {
            if (request.getUsername().isEmpty() || request.getPassword().isEmpty()) {
                responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("Username and password must not be empty").asRuntimeException());
                return;
            }

            User user = userRepository.getUser(request.getUsername(), request.getPassword());

            if (user == null) {
                responseObserver.onError(Status.NOT_FOUND.withDescription("User not found").asRuntimeException());
                return;
            }

            String jwt = JwtUtil.generateJwt(
                    user.getIdUser(),
                    user.getRole(),
                    user.getUsername(),
                    2880000L
            );

            AuthServiceOuterClass.AuthResponse response = AuthServiceOuterClass.AuthResponse.newBuilder()
                    .setToken(jwt)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onError(Status.UNAUTHENTICATED.withDescription("Invalid credentials").asRuntimeException());
        }
    }

    @Override
    public void logout(AuthServiceOuterClass.LogoutRequest request,
                         io.grpc.stub.StreamObserver<AuthServiceOuterClass.LogoutResponse> responseObserver) {
        System.out.println("Received request for logout");

        if (request.getToken().isEmpty()) {
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("Token must not be empty").asRuntimeException());
            return;
        }

        RedisUtil.blacklistToken(request.getToken());

        AuthServiceOuterClass.LogoutResponse response = AuthServiceOuterClass.LogoutResponse.newBuilder()
                .setSuccess(true)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void validate(AuthServiceOuterClass.ValidateRequest request,
                         io.grpc.stub.StreamObserver<AuthServiceOuterClass.ValidateResponse> responseObserver) {
        System.out.println("Received request for validation");

        if (request.getToken().isEmpty()) {
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("Token must not be empty").asRuntimeException());
            return;
        }

        String jwt = request.getToken().substring(7);

        if (!JwtUtil.validateJwt(jwt)) {
            responseObserver.onError(Status.UNAUTHENTICATED.withDescription("Invalid token").asRuntimeException());
            return;
        }

        if (RedisUtil.isBlacklisted(jwt)) {
            responseObserver.onError(Status.UNAUTHENTICATED.withDescription("JWT is blacklisted").asRuntimeException());
            return;
        }

        AuthServiceOuterClass.ValidateResponse response = AuthServiceOuterClass.ValidateResponse.newBuilder()
                .setIsValid(true)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void register(AuthServiceOuterClass.RegisterRequest request,
                         io.grpc.stub.StreamObserver<AuthServiceOuterClass.RegisterResponse> responseObserver) {
        System.out.println("Received request for registration");

        try {
            if (request.getUsername().isEmpty() || request.getPassword().isEmpty() || request.getRole().isEmpty()) {
                responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("Username, password and role must not be empty").asRuntimeException());
                return;
            }

            User user = userRepository.saveUser(request.getUsername(), request.getPassword(), request.getRole());

            AuthServiceOuterClass.RegisterResponse response = AuthServiceOuterClass.RegisterResponse.newBuilder()
                    .setUsername(user.getUsername())
                    .setUid(user.getIdUser())
                    .setRole(user.getRole())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            if (e.getMessage().equals("User already exists")) {
                responseObserver.onError(Status.ALREADY_EXISTS.withDescription(e.getMessage()).asRuntimeException());
            } else {
                e.printStackTrace();
                responseObserver.onError(Status.INTERNAL.withDescription("Internal Server Error").asRuntimeException());
            }
        }
    }

    @Override
    public void setBlacklist(AuthServiceOuterClass.BlacklistRequest request,
                               StreamObserver<AuthServiceOuterClass.BlacklisResponse> responseObserver) {
        System.out.println("Received request to blacklist token");

        try {
            if (request.getToken().isEmpty()) {
                responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("Token must not be empty").asRuntimeException());
                return;
            }

            RedisUtil.blacklistToken(request.getToken());

            AuthServiceOuterClass.BlacklisResponse response = AuthServiceOuterClass.BlacklisResponse.newBuilder()
                    .setSuccess(true)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onError(Status.INTERNAL.withDescription("Internal Server Error").asRuntimeException());
        }
    }

    @Override
    public void updatePassword(AuthServiceOuterClass.UpdatePasswordRequest request,
                               StreamObserver<AuthServiceOuterClass.UpdatePasswordResponse> responseObserver) {
        System.out.println("Received request to update password");

        try {
            if (request.getUsername().isEmpty() || request.getOldPassword().isEmpty() || request.getNewPassword().isEmpty()) {
                responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("Username, old password, and new password must not be empty").asRuntimeException());
                return;
            }

            User user = userRepository.getUser(request.getUsername(), request.getOldPassword());

            if (user == null) {
                responseObserver.onError(Status.NOT_FOUND.withDescription("User not found").asRuntimeException());
                return;
            }

            user.setPassword(request.getNewPassword());
            userRepository.updateUser(user);

            AuthServiceOuterClass.UpdatePasswordResponse response = AuthServiceOuterClass.UpdatePasswordResponse.newBuilder()
                    .setSuccess(true)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onError(Status.INTERNAL.withDescription("Internal Server Error").asRuntimeException());
        }
    }
}
