package com.proiect.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: AuthService.proto")
public final class AuthServiceGrpc {

  private AuthServiceGrpc() {}

  public static final String SERVICE_NAME = "AuthService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.proiect.proto.AuthServiceOuterClass.AuthRequest,
      com.proiect.proto.AuthServiceOuterClass.AuthResponse> getAuthMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Auth",
      requestType = com.proiect.proto.AuthServiceOuterClass.AuthRequest.class,
      responseType = com.proiect.proto.AuthServiceOuterClass.AuthResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.proiect.proto.AuthServiceOuterClass.AuthRequest,
      com.proiect.proto.AuthServiceOuterClass.AuthResponse> getAuthMethod() {
    io.grpc.MethodDescriptor<com.proiect.proto.AuthServiceOuterClass.AuthRequest, com.proiect.proto.AuthServiceOuterClass.AuthResponse> getAuthMethod;
    if ((getAuthMethod = AuthServiceGrpc.getAuthMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getAuthMethod = AuthServiceGrpc.getAuthMethod) == null) {
          AuthServiceGrpc.getAuthMethod = getAuthMethod = 
              io.grpc.MethodDescriptor.<com.proiect.proto.AuthServiceOuterClass.AuthRequest, com.proiect.proto.AuthServiceOuterClass.AuthResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "AuthService", "Auth"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.proiect.proto.AuthServiceOuterClass.AuthRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.proiect.proto.AuthServiceOuterClass.AuthResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("Auth"))
                  .build();
          }
        }
     }
     return getAuthMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.proiect.proto.AuthServiceOuterClass.LogoutRequest,
      com.proiect.proto.AuthServiceOuterClass.LogoutResponse> getLogoutMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Logout",
      requestType = com.proiect.proto.AuthServiceOuterClass.LogoutRequest.class,
      responseType = com.proiect.proto.AuthServiceOuterClass.LogoutResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.proiect.proto.AuthServiceOuterClass.LogoutRequest,
      com.proiect.proto.AuthServiceOuterClass.LogoutResponse> getLogoutMethod() {
    io.grpc.MethodDescriptor<com.proiect.proto.AuthServiceOuterClass.LogoutRequest, com.proiect.proto.AuthServiceOuterClass.LogoutResponse> getLogoutMethod;
    if ((getLogoutMethod = AuthServiceGrpc.getLogoutMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getLogoutMethod = AuthServiceGrpc.getLogoutMethod) == null) {
          AuthServiceGrpc.getLogoutMethod = getLogoutMethod = 
              io.grpc.MethodDescriptor.<com.proiect.proto.AuthServiceOuterClass.LogoutRequest, com.proiect.proto.AuthServiceOuterClass.LogoutResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "AuthService", "Logout"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.proiect.proto.AuthServiceOuterClass.LogoutRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.proiect.proto.AuthServiceOuterClass.LogoutResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("Logout"))
                  .build();
          }
        }
     }
     return getLogoutMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.proiect.proto.AuthServiceOuterClass.ValidateRequest,
      com.proiect.proto.AuthServiceOuterClass.ValidateResponse> getValidateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Validate",
      requestType = com.proiect.proto.AuthServiceOuterClass.ValidateRequest.class,
      responseType = com.proiect.proto.AuthServiceOuterClass.ValidateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.proiect.proto.AuthServiceOuterClass.ValidateRequest,
      com.proiect.proto.AuthServiceOuterClass.ValidateResponse> getValidateMethod() {
    io.grpc.MethodDescriptor<com.proiect.proto.AuthServiceOuterClass.ValidateRequest, com.proiect.proto.AuthServiceOuterClass.ValidateResponse> getValidateMethod;
    if ((getValidateMethod = AuthServiceGrpc.getValidateMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getValidateMethod = AuthServiceGrpc.getValidateMethod) == null) {
          AuthServiceGrpc.getValidateMethod = getValidateMethod = 
              io.grpc.MethodDescriptor.<com.proiect.proto.AuthServiceOuterClass.ValidateRequest, com.proiect.proto.AuthServiceOuterClass.ValidateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "AuthService", "Validate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.proiect.proto.AuthServiceOuterClass.ValidateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.proiect.proto.AuthServiceOuterClass.ValidateResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("Validate"))
                  .build();
          }
        }
     }
     return getValidateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.proiect.proto.AuthServiceOuterClass.RegisterRequest,
      com.proiect.proto.AuthServiceOuterClass.RegisterResponse> getRegisterMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Register",
      requestType = com.proiect.proto.AuthServiceOuterClass.RegisterRequest.class,
      responseType = com.proiect.proto.AuthServiceOuterClass.RegisterResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.proiect.proto.AuthServiceOuterClass.RegisterRequest,
      com.proiect.proto.AuthServiceOuterClass.RegisterResponse> getRegisterMethod() {
    io.grpc.MethodDescriptor<com.proiect.proto.AuthServiceOuterClass.RegisterRequest, com.proiect.proto.AuthServiceOuterClass.RegisterResponse> getRegisterMethod;
    if ((getRegisterMethod = AuthServiceGrpc.getRegisterMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getRegisterMethod = AuthServiceGrpc.getRegisterMethod) == null) {
          AuthServiceGrpc.getRegisterMethod = getRegisterMethod = 
              io.grpc.MethodDescriptor.<com.proiect.proto.AuthServiceOuterClass.RegisterRequest, com.proiect.proto.AuthServiceOuterClass.RegisterResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "AuthService", "Register"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.proiect.proto.AuthServiceOuterClass.RegisterRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.proiect.proto.AuthServiceOuterClass.RegisterResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("Register"))
                  .build();
          }
        }
     }
     return getRegisterMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.proiect.proto.AuthServiceOuterClass.BlacklistRequest,
      com.proiect.proto.AuthServiceOuterClass.BlacklisResponse> getSetBlacklistMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SetBlacklist",
      requestType = com.proiect.proto.AuthServiceOuterClass.BlacklistRequest.class,
      responseType = com.proiect.proto.AuthServiceOuterClass.BlacklisResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.proiect.proto.AuthServiceOuterClass.BlacklistRequest,
      com.proiect.proto.AuthServiceOuterClass.BlacklisResponse> getSetBlacklistMethod() {
    io.grpc.MethodDescriptor<com.proiect.proto.AuthServiceOuterClass.BlacklistRequest, com.proiect.proto.AuthServiceOuterClass.BlacklisResponse> getSetBlacklistMethod;
    if ((getSetBlacklistMethod = AuthServiceGrpc.getSetBlacklistMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getSetBlacklistMethod = AuthServiceGrpc.getSetBlacklistMethod) == null) {
          AuthServiceGrpc.getSetBlacklistMethod = getSetBlacklistMethod = 
              io.grpc.MethodDescriptor.<com.proiect.proto.AuthServiceOuterClass.BlacklistRequest, com.proiect.proto.AuthServiceOuterClass.BlacklisResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "AuthService", "SetBlacklist"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.proiect.proto.AuthServiceOuterClass.BlacklistRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.proiect.proto.AuthServiceOuterClass.BlacklisResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("SetBlacklist"))
                  .build();
          }
        }
     }
     return getSetBlacklistMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.proiect.proto.AuthServiceOuterClass.UpdatePasswordRequest,
      com.proiect.proto.AuthServiceOuterClass.UpdatePasswordResponse> getUpdatePasswordMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdatePassword",
      requestType = com.proiect.proto.AuthServiceOuterClass.UpdatePasswordRequest.class,
      responseType = com.proiect.proto.AuthServiceOuterClass.UpdatePasswordResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.proiect.proto.AuthServiceOuterClass.UpdatePasswordRequest,
      com.proiect.proto.AuthServiceOuterClass.UpdatePasswordResponse> getUpdatePasswordMethod() {
    io.grpc.MethodDescriptor<com.proiect.proto.AuthServiceOuterClass.UpdatePasswordRequest, com.proiect.proto.AuthServiceOuterClass.UpdatePasswordResponse> getUpdatePasswordMethod;
    if ((getUpdatePasswordMethod = AuthServiceGrpc.getUpdatePasswordMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getUpdatePasswordMethod = AuthServiceGrpc.getUpdatePasswordMethod) == null) {
          AuthServiceGrpc.getUpdatePasswordMethod = getUpdatePasswordMethod = 
              io.grpc.MethodDescriptor.<com.proiect.proto.AuthServiceOuterClass.UpdatePasswordRequest, com.proiect.proto.AuthServiceOuterClass.UpdatePasswordResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "AuthService", "UpdatePassword"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.proiect.proto.AuthServiceOuterClass.UpdatePasswordRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.proiect.proto.AuthServiceOuterClass.UpdatePasswordResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("UpdatePassword"))
                  .build();
          }
        }
     }
     return getUpdatePasswordMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AuthServiceStub newStub(io.grpc.Channel channel) {
    return new AuthServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AuthServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new AuthServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AuthServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new AuthServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class AuthServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void auth(com.proiect.proto.AuthServiceOuterClass.AuthRequest request,
        io.grpc.stub.StreamObserver<com.proiect.proto.AuthServiceOuterClass.AuthResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getAuthMethod(), responseObserver);
    }

    /**
     */
    public void logout(com.proiect.proto.AuthServiceOuterClass.LogoutRequest request,
        io.grpc.stub.StreamObserver<com.proiect.proto.AuthServiceOuterClass.LogoutResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getLogoutMethod(), responseObserver);
    }

    /**
     */
    public void validate(com.proiect.proto.AuthServiceOuterClass.ValidateRequest request,
        io.grpc.stub.StreamObserver<com.proiect.proto.AuthServiceOuterClass.ValidateResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getValidateMethod(), responseObserver);
    }

    /**
     */
    public void register(com.proiect.proto.AuthServiceOuterClass.RegisterRequest request,
        io.grpc.stub.StreamObserver<com.proiect.proto.AuthServiceOuterClass.RegisterResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRegisterMethod(), responseObserver);
    }

    /**
     */
    public void setBlacklist(com.proiect.proto.AuthServiceOuterClass.BlacklistRequest request,
        io.grpc.stub.StreamObserver<com.proiect.proto.AuthServiceOuterClass.BlacklisResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getSetBlacklistMethod(), responseObserver);
    }

    /**
     */
    public void updatePassword(com.proiect.proto.AuthServiceOuterClass.UpdatePasswordRequest request,
        io.grpc.stub.StreamObserver<com.proiect.proto.AuthServiceOuterClass.UpdatePasswordResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdatePasswordMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAuthMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.proiect.proto.AuthServiceOuterClass.AuthRequest,
                com.proiect.proto.AuthServiceOuterClass.AuthResponse>(
                  this, METHODID_AUTH)))
          .addMethod(
            getLogoutMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.proiect.proto.AuthServiceOuterClass.LogoutRequest,
                com.proiect.proto.AuthServiceOuterClass.LogoutResponse>(
                  this, METHODID_LOGOUT)))
          .addMethod(
            getValidateMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.proiect.proto.AuthServiceOuterClass.ValidateRequest,
                com.proiect.proto.AuthServiceOuterClass.ValidateResponse>(
                  this, METHODID_VALIDATE)))
          .addMethod(
            getRegisterMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.proiect.proto.AuthServiceOuterClass.RegisterRequest,
                com.proiect.proto.AuthServiceOuterClass.RegisterResponse>(
                  this, METHODID_REGISTER)))
          .addMethod(
            getSetBlacklistMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.proiect.proto.AuthServiceOuterClass.BlacklistRequest,
                com.proiect.proto.AuthServiceOuterClass.BlacklisResponse>(
                  this, METHODID_SET_BLACKLIST)))
          .addMethod(
            getUpdatePasswordMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.proiect.proto.AuthServiceOuterClass.UpdatePasswordRequest,
                com.proiect.proto.AuthServiceOuterClass.UpdatePasswordResponse>(
                  this, METHODID_UPDATE_PASSWORD)))
          .build();
    }
  }

  /**
   */
  public static final class AuthServiceStub extends io.grpc.stub.AbstractStub<AuthServiceStub> {
    private AuthServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AuthServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AuthServiceStub(channel, callOptions);
    }

    /**
     */
    public void auth(com.proiect.proto.AuthServiceOuterClass.AuthRequest request,
        io.grpc.stub.StreamObserver<com.proiect.proto.AuthServiceOuterClass.AuthResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAuthMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void logout(com.proiect.proto.AuthServiceOuterClass.LogoutRequest request,
        io.grpc.stub.StreamObserver<com.proiect.proto.AuthServiceOuterClass.LogoutResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getLogoutMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void validate(com.proiect.proto.AuthServiceOuterClass.ValidateRequest request,
        io.grpc.stub.StreamObserver<com.proiect.proto.AuthServiceOuterClass.ValidateResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getValidateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void register(com.proiect.proto.AuthServiceOuterClass.RegisterRequest request,
        io.grpc.stub.StreamObserver<com.proiect.proto.AuthServiceOuterClass.RegisterResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRegisterMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void setBlacklist(com.proiect.proto.AuthServiceOuterClass.BlacklistRequest request,
        io.grpc.stub.StreamObserver<com.proiect.proto.AuthServiceOuterClass.BlacklisResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSetBlacklistMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updatePassword(com.proiect.proto.AuthServiceOuterClass.UpdatePasswordRequest request,
        io.grpc.stub.StreamObserver<com.proiect.proto.AuthServiceOuterClass.UpdatePasswordResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdatePasswordMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class AuthServiceBlockingStub extends io.grpc.stub.AbstractStub<AuthServiceBlockingStub> {
    private AuthServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AuthServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AuthServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.proiect.proto.AuthServiceOuterClass.AuthResponse auth(com.proiect.proto.AuthServiceOuterClass.AuthRequest request) {
      return blockingUnaryCall(
          getChannel(), getAuthMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.proiect.proto.AuthServiceOuterClass.LogoutResponse logout(com.proiect.proto.AuthServiceOuterClass.LogoutRequest request) {
      return blockingUnaryCall(
          getChannel(), getLogoutMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.proiect.proto.AuthServiceOuterClass.ValidateResponse validate(com.proiect.proto.AuthServiceOuterClass.ValidateRequest request) {
      return blockingUnaryCall(
          getChannel(), getValidateMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.proiect.proto.AuthServiceOuterClass.RegisterResponse register(com.proiect.proto.AuthServiceOuterClass.RegisterRequest request) {
      return blockingUnaryCall(
          getChannel(), getRegisterMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.proiect.proto.AuthServiceOuterClass.BlacklisResponse setBlacklist(com.proiect.proto.AuthServiceOuterClass.BlacklistRequest request) {
      return blockingUnaryCall(
          getChannel(), getSetBlacklistMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.proiect.proto.AuthServiceOuterClass.UpdatePasswordResponse updatePassword(com.proiect.proto.AuthServiceOuterClass.UpdatePasswordRequest request) {
      return blockingUnaryCall(
          getChannel(), getUpdatePasswordMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class AuthServiceFutureStub extends io.grpc.stub.AbstractStub<AuthServiceFutureStub> {
    private AuthServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AuthServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AuthServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.proiect.proto.AuthServiceOuterClass.AuthResponse> auth(
        com.proiect.proto.AuthServiceOuterClass.AuthRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getAuthMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.proiect.proto.AuthServiceOuterClass.LogoutResponse> logout(
        com.proiect.proto.AuthServiceOuterClass.LogoutRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getLogoutMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.proiect.proto.AuthServiceOuterClass.ValidateResponse> validate(
        com.proiect.proto.AuthServiceOuterClass.ValidateRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getValidateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.proiect.proto.AuthServiceOuterClass.RegisterResponse> register(
        com.proiect.proto.AuthServiceOuterClass.RegisterRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRegisterMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.proiect.proto.AuthServiceOuterClass.BlacklisResponse> setBlacklist(
        com.proiect.proto.AuthServiceOuterClass.BlacklistRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSetBlacklistMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.proiect.proto.AuthServiceOuterClass.UpdatePasswordResponse> updatePassword(
        com.proiect.proto.AuthServiceOuterClass.UpdatePasswordRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdatePasswordMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_AUTH = 0;
  private static final int METHODID_LOGOUT = 1;
  private static final int METHODID_VALIDATE = 2;
  private static final int METHODID_REGISTER = 3;
  private static final int METHODID_SET_BLACKLIST = 4;
  private static final int METHODID_UPDATE_PASSWORD = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AuthServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(AuthServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_AUTH:
          serviceImpl.auth((com.proiect.proto.AuthServiceOuterClass.AuthRequest) request,
              (io.grpc.stub.StreamObserver<com.proiect.proto.AuthServiceOuterClass.AuthResponse>) responseObserver);
          break;
        case METHODID_LOGOUT:
          serviceImpl.logout((com.proiect.proto.AuthServiceOuterClass.LogoutRequest) request,
              (io.grpc.stub.StreamObserver<com.proiect.proto.AuthServiceOuterClass.LogoutResponse>) responseObserver);
          break;
        case METHODID_VALIDATE:
          serviceImpl.validate((com.proiect.proto.AuthServiceOuterClass.ValidateRequest) request,
              (io.grpc.stub.StreamObserver<com.proiect.proto.AuthServiceOuterClass.ValidateResponse>) responseObserver);
          break;
        case METHODID_REGISTER:
          serviceImpl.register((com.proiect.proto.AuthServiceOuterClass.RegisterRequest) request,
              (io.grpc.stub.StreamObserver<com.proiect.proto.AuthServiceOuterClass.RegisterResponse>) responseObserver);
          break;
        case METHODID_SET_BLACKLIST:
          serviceImpl.setBlacklist((com.proiect.proto.AuthServiceOuterClass.BlacklistRequest) request,
              (io.grpc.stub.StreamObserver<com.proiect.proto.AuthServiceOuterClass.BlacklisResponse>) responseObserver);
          break;
        case METHODID_UPDATE_PASSWORD:
          serviceImpl.updatePassword((com.proiect.proto.AuthServiceOuterClass.UpdatePasswordRequest) request,
              (io.grpc.stub.StreamObserver<com.proiect.proto.AuthServiceOuterClass.UpdatePasswordResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class AuthServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AuthServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.proiect.proto.AuthServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AuthService");
    }
  }

  private static final class AuthServiceFileDescriptorSupplier
      extends AuthServiceBaseDescriptorSupplier {
    AuthServiceFileDescriptorSupplier() {}
  }

  private static final class AuthServiceMethodDescriptorSupplier
      extends AuthServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    AuthServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (AuthServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AuthServiceFileDescriptorSupplier())
              .addMethod(getAuthMethod())
              .addMethod(getLogoutMethod())
              .addMethod(getValidateMethod())
              .addMethod(getRegisterMethod())
              .addMethod(getSetBlacklistMethod())
              .addMethod(getUpdatePasswordMethod())
              .build();
        }
      }
    }
    return result;
  }
}
