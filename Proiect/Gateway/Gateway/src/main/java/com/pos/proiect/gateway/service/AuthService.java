package com.pos.proiect.gateway.service;

import com.proiect.proto.AuthServiceGrpc;
import com.proiect.proto.AuthServiceOuterClass;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

@GRpcService
public class AuthServiceImpl implements AuthService {

    private final AuthServiceGrpc.AuthServiceStub authServiceStub;

    @Autowired
    public AuthServiceImpl(AuthServiceGrpc.AuthServiceStub authServiceStub) {
        this.authServiceStub = authServiceStub;
    }

    @Override
    public Mono<AuthServiceOuterClass.AuthResponse> authenticate(AuthServiceOuterClass.AuthRequest request) {
        return Mono.create(sink -> {
            authServiceStub.auth(request, new GrpcStreamObserver<>(sink));
        });
    }

    // Implement other methods similar to authenticate
}
