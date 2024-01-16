package org.proiect.springclient.controller;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.proiect.springclient.proto.AuthServiceGrpc;
import org.proiect.springclient.proto.AuthServiceOuterClass;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthServiceGrpc.AuthServiceBlockingStub authServiceBlockingStub;

    public AuthController() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        this.authServiceBlockingStub = AuthServiceGrpc.newBlockingStub(channel);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthServiceOuterClass.AuthRequest request) {
        try {
            AuthServiceOuterClass.AuthResponse response = authServiceBlockingStub.auth(request);

            Link selfLink = WebMvcLinkBuilder.linkTo(getClass()).slash("login").withSelfRel();
            Link parentLink = WebMvcLinkBuilder.linkTo(getClass()).withRel("parent");

            EntityModel<Object> resource = EntityModel.of(response, selfLink, parentLink);
            return ResponseEntity.status(HttpStatus.CREATED).body(resource);
        } catch (Exception e) {
            return createErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid credentials", "INVALID_CREDENTIALS");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@RequestBody AuthServiceOuterClass.LogoutRequest request) {
        try {
            if (request.getToken().isEmpty()) {
                return createErrorResponse(HttpStatus.BAD_REQUEST, "Token must not be empty", "TOKEN_EMPTY");
            }

            AuthServiceOuterClass.LogoutResponse response = authServiceBlockingStub.logout(request);

            Link selfLink = WebMvcLinkBuilder.linkTo(getClass()).slash("logout").withSelfRel();
            Link parentLink = WebMvcLinkBuilder.linkTo(getClass()).withRel("parent");

            EntityModel<Object> resource = EntityModel.of(response, selfLink, parentLink);
            return ResponseEntity.status(HttpStatus.OK).body(resource);
        } catch (Exception e) {
            return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "INTERNAL_ERROR");
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<Object> validate(@RequestBody AuthServiceOuterClass.ValidateRequest request) {
        try {
            if (request.getToken().isEmpty()) {
                return createErrorResponse(HttpStatus.BAD_REQUEST, "Token must not be empty", "TOKEN_EMPTY");
            }

            // Your validation logic

            AuthServiceOuterClass.ValidateResponse response = AuthServiceOuterClass.ValidateResponse.newBuilder()
                    .setIsValid(true)
                    .build();

            Link selfLink = WebMvcLinkBuilder.linkTo(getClass()).slash("validate").withSelfRel();
            Link parentLink = WebMvcLinkBuilder.linkTo(getClass()).withRel("parent");

            EntityModel<Object> resource = EntityModel.of(response, selfLink, parentLink);
            return ResponseEntity.status(HttpStatus.OK).body(resource);
        } catch (Exception e) {
            return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "INTERNAL_ERROR");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody AuthServiceOuterClass.RegisterRequest request) {
        try {
            if (request.getUsername().isEmpty() || request.getPassword().isEmpty() || request.getRole().isEmpty()) {
                return createErrorResponse(HttpStatus.BAD_REQUEST, "Username, password, and role must not be empty", "INVALID_INPUT");
            }

            // Your registration logic

            AuthServiceOuterClass.RegisterResponse response = AuthServiceOuterClass.RegisterResponse.newBuilder()
                    .setUsername("exampleUsername")
                    .setUid("exampleUid")
                    .setRole("exampleRole")
                    .build();

            Link selfLink = WebMvcLinkBuilder.linkTo(getClass()).slash("register").withSelfRel();
            Link parentLink = WebMvcLinkBuilder.linkTo(getClass()).withRel("parent");

            EntityModel<Object> resource = EntityModel.of(response, selfLink, parentLink);
            return ResponseEntity.status(HttpStatus.CREATED).body(resource);
        } catch (Exception e) {
            return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "INTERNAL_ERROR");
        }
    }

    @PostMapping("/setBlacklist")
    public ResponseEntity<Object> setBlacklist(@RequestBody AuthServiceOuterClass.BlacklistRequest request) {
        try {
            if (request.getToken().isEmpty()) {
                return createErrorResponse(HttpStatus.BAD_REQUEST, "Token must not be empty", "TOKEN_EMPTY");
            }

            // Your setBlacklist logic

            AuthServiceOuterClass.BlacklisResponse response = AuthServiceOuterClass.BlacklisResponse.newBuilder()
                    .setSuccess(true)
                    .build();

            Link selfLink = WebMvcLinkBuilder.linkTo(getClass()).slash("setBlacklist").withSelfRel();
            Link parentLink = WebMvcLinkBuilder.linkTo(getClass()).withRel("parent");

            EntityModel<Object> resource = EntityModel.of(response, selfLink, parentLink);
            return ResponseEntity.status(HttpStatus.OK).body(resource);
        } catch (Exception e) {
            return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "INTERNAL_ERROR");
        }
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<Object> updatePassword(@RequestBody AuthServiceOuterClass.UpdatePasswordRequest request) {
        try {
            if (request.getUsername().isEmpty() || request.getOldPassword().isEmpty() || request.getNewPassword().isEmpty()) {
                return createErrorResponse(HttpStatus.BAD_REQUEST, "Username, old password, and new password must not be empty", "INVALID_INPUT");
            }

            // Your updatePassword logic

            AuthServiceOuterClass.UpdatePasswordResponse response = AuthServiceOuterClass.UpdatePasswordResponse.newBuilder()
                    .setSuccess(true)
                    .build();

            Link selfLink = WebMvcLinkBuilder.linkTo(getClass()).slash("updatePassword").withSelfRel();
            Link parentLink = WebMvcLinkBuilder.linkTo(getClass()).withRel("parent");

            EntityModel<Object> resource = EntityModel.of(response, selfLink, parentLink);
            return ResponseEntity.status(HttpStatus.OK).body(resource);
        } catch (Exception e) {
            return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "INTERNAL_ERROR");
        }


    }

    private ResponseEntity<Object> createErrorResponse(HttpStatus status, String message, String errorCode) {
        return ResponseEntity.status(status).body(new ErrorResponse(message, errorCode));
    }

    private static class ErrorResponse {
        private final String message;
        private final String errorCode;

        public ErrorResponse(String message, String errorCode) {
            this.message = message;
            this.errorCode = errorCode;
        }

        public String getMessage() {
            return message;
        }

        public String getErrorCode() {
            return errorCode;
        }
    }
}
