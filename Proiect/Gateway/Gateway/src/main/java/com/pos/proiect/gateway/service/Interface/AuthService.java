package com.pos.proiect.gateway.service.Interface;

import com.proiect.proto.AuthServiceOuterClass;
import reactor.core.publisher.Mono;

public interface AuthService {
//    rpc Logout (LogoutRequest) returns (LogoutResponse) {}
//    rpc Validate (ValidateRequest) returns (ValidateResponse) {}
//    rpc Register (RegisterRequest) returns (RegisterResponse) {}
//    rpc SetBlacklist (BlacklistRequest) returns (BlacklisResponse) {}
//    rpc UpdatePassword (UpdatePasswordRequest) returns (UpdatePasswordResponse) {}
    Mono<AuthServiceOuterClass.AuthResponse> authenticate(AuthServiceOuterClass.AuthRequest request);
    Mono<AuthServiceOuterClass.LogoutResponse> logout(AuthServiceOuterClass.LogoutRequest request);
    Mono<AuthServiceOuterClass.ValidateResponse> validate(AuthServiceOuterClass.ValidateRequest request);
    Mono<AuthServiceOuterClass.RegisterResponse> register(AuthServiceOuterClass.RegisterRequest request);
    Mono<AuthServiceOuterClass.BlacklisResponse> setBlacklist(AuthServiceOuterClass.BlacklistRequest request);
    Mono<AuthServiceOuterClass.UpdatePasswordResponse> updatePassword(AuthServiceOuterClass.UpdatePasswordRequest request);
}
