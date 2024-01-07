package org.proiect.gateway_ex.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
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
  private static volatile io.grpc.MethodDescriptor<AuthServiceOuterClass.AuthRequest,
      AuthServiceOuterClass.AuthResponse> getAuthMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Auth",
      requestType = AuthServiceOuterClass.AuthRequest.class,
      responseType = AuthServiceOuterClass.AuthResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<AuthServiceOuterClass.AuthRequest,
      AuthServiceOuterClass.AuthResponse> getAuthMethod() {
    io.grpc.MethodDescriptor<AuthServiceOuterClass.AuthRequest, AuthServiceOuterClass.AuthResponse> getAuthMethod;
    if ((getAuthMethod = AuthServiceGrpc.getAuthMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getAuthMethod = AuthServiceGrpc.getAuthMethod) == null) {
          AuthServiceGrpc.getAuthMethod = getAuthMethod = 
              io.grpc.MethodDescriptor.<AuthServiceOuterClass.AuthRequest, AuthServiceOuterClass.AuthResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "AuthService", "Auth"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AuthServiceOuterClass.AuthRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AuthServiceOuterClass.AuthResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("Auth"))
                  .build();
          }
        }
     }
     return getAuthMethod;
  }

  private static volatile io.grpc.MethodDescriptor<AuthServiceOuterClass.LogoutRequest,
      AuthServiceOuterClass.LogoutResponse> getLogoutMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Logout",
      requestType = AuthServiceOuterClass.LogoutRequest.class,
      responseType = AuthServiceOuterClass.LogoutResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<AuthServiceOuterClass.LogoutRequest,
      AuthServiceOuterClass.LogoutResponse> getLogoutMethod() {
    io.grpc.MethodDescriptor<AuthServiceOuterClass.LogoutRequest, AuthServiceOuterClass.LogoutResponse> getLogoutMethod;
    if ((getLogoutMethod = AuthServiceGrpc.getLogoutMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getLogoutMethod = AuthServiceGrpc.getLogoutMethod) == null) {
          AuthServiceGrpc.getLogoutMethod = getLogoutMethod = 
              io.grpc.MethodDescriptor.<AuthServiceOuterClass.LogoutRequest, AuthServiceOuterClass.LogoutResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "AuthService", "Logout"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AuthServiceOuterClass.LogoutRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AuthServiceOuterClass.LogoutResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("Logout"))
                  .build();
          }
        }
     }
     return getLogoutMethod;
  }

  private static volatile io.grpc.MethodDescriptor<AuthServiceOuterClass.ValidateRequest,
      AuthServiceOuterClass.ValidateResponse> getValidateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Validate",
      requestType = AuthServiceOuterClass.ValidateRequest.class,
      responseType = AuthServiceOuterClass.ValidateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<AuthServiceOuterClass.ValidateRequest,
      AuthServiceOuterClass.ValidateResponse> getValidateMethod() {
    io.grpc.MethodDescriptor<AuthServiceOuterClass.ValidateRequest, AuthServiceOuterClass.ValidateResponse> getValidateMethod;
    if ((getValidateMethod = AuthServiceGrpc.getValidateMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getValidateMethod = AuthServiceGrpc.getValidateMethod) == null) {
          AuthServiceGrpc.getValidateMethod = getValidateMethod = 
              io.grpc.MethodDescriptor.<AuthServiceOuterClass.ValidateRequest, AuthServiceOuterClass.ValidateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "AuthService", "Validate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AuthServiceOuterClass.ValidateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AuthServiceOuterClass.ValidateResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("Validate"))
                  .build();
          }
        }
     }
     return getValidateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<AuthServiceOuterClass.RegisterRequest,
      AuthServiceOuterClass.RegisterResponse> getRegisterMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Register",
      requestType = AuthServiceOuterClass.RegisterRequest.class,
      responseType = AuthServiceOuterClass.RegisterResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<AuthServiceOuterClass.RegisterRequest,
      AuthServiceOuterClass.RegisterResponse> getRegisterMethod() {
    io.grpc.MethodDescriptor<AuthServiceOuterClass.RegisterRequest, AuthServiceOuterClass.RegisterResponse> getRegisterMethod;
    if ((getRegisterMethod = AuthServiceGrpc.getRegisterMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getRegisterMethod = AuthServiceGrpc.getRegisterMethod) == null) {
          AuthServiceGrpc.getRegisterMethod = getRegisterMethod = 
              io.grpc.MethodDescriptor.<AuthServiceOuterClass.RegisterRequest, AuthServiceOuterClass.RegisterResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "AuthService", "Register"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AuthServiceOuterClass.RegisterRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AuthServiceOuterClass.RegisterResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("Register"))
                  .build();
          }
        }
     }
     return getRegisterMethod;
  }

  private static volatile io.grpc.MethodDescriptor<AuthServiceOuterClass.BlacklistRequest,
      AuthServiceOuterClass.BlacklisResponse> getSetBlacklistMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SetBlacklist",
      requestType = AuthServiceOuterClass.BlacklistRequest.class,
      responseType = AuthServiceOuterClass.BlacklisResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<AuthServiceOuterClass.BlacklistRequest,
      AuthServiceOuterClass.BlacklisResponse> getSetBlacklistMethod() {
    io.grpc.MethodDescriptor<AuthServiceOuterClass.BlacklistRequest, AuthServiceOuterClass.BlacklisResponse> getSetBlacklistMethod;
    if ((getSetBlacklistMethod = AuthServiceGrpc.getSetBlacklistMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getSetBlacklistMethod = AuthServiceGrpc.getSetBlacklistMethod) == null) {
          AuthServiceGrpc.getSetBlacklistMethod = getSetBlacklistMethod = 
              io.grpc.MethodDescriptor.<AuthServiceOuterClass.BlacklistRequest, AuthServiceOuterClass.BlacklisResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "AuthService", "SetBlacklist"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AuthServiceOuterClass.BlacklistRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AuthServiceOuterClass.BlacklisResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("SetBlacklist"))
                  .build();
          }
        }
     }
     return getSetBlacklistMethod;
  }

  private static volatile io.grpc.MethodDescriptor<AuthServiceOuterClass.UpdatePasswordRequest,
      AuthServiceOuterClass.UpdatePasswordResponse> getUpdatePasswordMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdatePassword",
      requestType = AuthServiceOuterClass.UpdatePasswordRequest.class,
      responseType = AuthServiceOuterClass.UpdatePasswordResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<AuthServiceOuterClass.UpdatePasswordRequest,
      AuthServiceOuterClass.UpdatePasswordResponse> getUpdatePasswordMethod() {
    io.grpc.MethodDescriptor<AuthServiceOuterClass.UpdatePasswordRequest, AuthServiceOuterClass.UpdatePasswordResponse> getUpdatePasswordMethod;
    if ((getUpdatePasswordMethod = AuthServiceGrpc.getUpdatePasswordMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getUpdatePasswordMethod = AuthServiceGrpc.getUpdatePasswordMethod) == null) {
          AuthServiceGrpc.getUpdatePasswordMethod = getUpdatePasswordMethod = 
              io.grpc.MethodDescriptor.<AuthServiceOuterClass.UpdatePasswordRequest, AuthServiceOuterClass.UpdatePasswordResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "AuthService", "UpdatePassword"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AuthServiceOuterClass.UpdatePasswordRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AuthServiceOuterClass.UpdatePasswordResponse.getDefaultInstance()))
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
    public void auth(AuthServiceOuterClass.AuthRequest request,
                     io.grpc.stub.StreamObserver<AuthServiceOuterClass.AuthResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getAuthMethod(), responseObserver);
    }

    /**
     */
    public void logout(AuthServiceOuterClass.LogoutRequest request,
                       io.grpc.stub.StreamObserver<AuthServiceOuterClass.LogoutResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getLogoutMethod(), responseObserver);
    }

    /**
     */
    public void validate(AuthServiceOuterClass.ValidateRequest request,
                         io.grpc.stub.StreamObserver<AuthServiceOuterClass.ValidateResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getValidateMethod(), responseObserver);
    }

    /**
     */
    public void register(AuthServiceOuterClass.RegisterRequest request,
                         io.grpc.stub.StreamObserver<AuthServiceOuterClass.RegisterResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRegisterMethod(), responseObserver);
    }

    /**
     */
    public void setBlacklist(AuthServiceOuterClass.BlacklistRequest request,
                             io.grpc.stub.StreamObserver<AuthServiceOuterClass.BlacklisResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getSetBlacklistMethod(), responseObserver);
    }

    /**
     */
    public void updatePassword(AuthServiceOuterClass.UpdatePasswordRequest request,
                               io.grpc.stub.StreamObserver<AuthServiceOuterClass.UpdatePasswordResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdatePasswordMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAuthMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                AuthServiceOuterClass.AuthRequest,
                AuthServiceOuterClass.AuthResponse>(
                  this, METHODID_AUTH)))
          .addMethod(
            getLogoutMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                AuthServiceOuterClass.LogoutRequest,
                AuthServiceOuterClass.LogoutResponse>(
                  this, METHODID_LOGOUT)))
          .addMethod(
            getValidateMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                AuthServiceOuterClass.ValidateRequest,
                AuthServiceOuterClass.ValidateResponse>(
                  this, METHODID_VALIDATE)))
          .addMethod(
            getRegisterMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                AuthServiceOuterClass.RegisterRequest,
                AuthServiceOuterClass.RegisterResponse>(
                  this, METHODID_REGISTER)))
          .addMethod(
            getSetBlacklistMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                AuthServiceOuterClass.BlacklistRequest,
                AuthServiceOuterClass.BlacklisResponse>(
                  this, METHODID_SET_BLACKLIST)))
          .addMethod(
            getUpdatePasswordMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                AuthServiceOuterClass.UpdatePasswordRequest,
                AuthServiceOuterClass.UpdatePasswordResponse>(
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
    public void auth(AuthServiceOuterClass.AuthRequest request,
                     io.grpc.stub.StreamObserver<AuthServiceOuterClass.AuthResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAuthMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void logout(AuthServiceOuterClass.LogoutRequest request,
                       io.grpc.stub.StreamObserver<AuthServiceOuterClass.LogoutResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getLogoutMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void validate(AuthServiceOuterClass.ValidateRequest request,
                         io.grpc.stub.StreamObserver<AuthServiceOuterClass.ValidateResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getValidateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void register(AuthServiceOuterClass.RegisterRequest request,
                         io.grpc.stub.StreamObserver<AuthServiceOuterClass.RegisterResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRegisterMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void setBlacklist(AuthServiceOuterClass.BlacklistRequest request,
                             io.grpc.stub.StreamObserver<AuthServiceOuterClass.BlacklisResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSetBlacklistMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updatePassword(AuthServiceOuterClass.UpdatePasswordRequest request,
                               io.grpc.stub.StreamObserver<AuthServiceOuterClass.UpdatePasswordResponse> responseObserver) {
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
    public AuthServiceOuterClass.AuthResponse auth(AuthServiceOuterClass.AuthRequest request) {
      return blockingUnaryCall(
          getChannel(), getAuthMethod(), getCallOptions(), request);
    }

    /**
     */
    public AuthServiceOuterClass.LogoutResponse logout(AuthServiceOuterClass.LogoutRequest request) {
      return blockingUnaryCall(
          getChannel(), getLogoutMethod(), getCallOptions(), request);
    }

    /**
     */
    public AuthServiceOuterClass.ValidateResponse validate(AuthServiceOuterClass.ValidateRequest request) {
      return blockingUnaryCall(
          getChannel(), getValidateMethod(), getCallOptions(), request);
    }

    /**
     */
    public AuthServiceOuterClass.RegisterResponse register(AuthServiceOuterClass.RegisterRequest request) {
      return blockingUnaryCall(
          getChannel(), getRegisterMethod(), getCallOptions(), request);
    }

    /**
     */
    public AuthServiceOuterClass.BlacklisResponse setBlacklist(AuthServiceOuterClass.BlacklistRequest request) {
      return blockingUnaryCall(
          getChannel(), getSetBlacklistMethod(), getCallOptions(), request);
    }

    /**
     */
    public AuthServiceOuterClass.UpdatePasswordResponse updatePassword(AuthServiceOuterClass.UpdatePasswordRequest request) {
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
    public com.google.common.util.concurrent.ListenableFuture<AuthServiceOuterClass.AuthResponse> auth(
        AuthServiceOuterClass.AuthRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getAuthMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<AuthServiceOuterClass.LogoutResponse> logout(
        AuthServiceOuterClass.LogoutRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getLogoutMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<AuthServiceOuterClass.ValidateResponse> validate(
        AuthServiceOuterClass.ValidateRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getValidateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<AuthServiceOuterClass.RegisterResponse> register(
        AuthServiceOuterClass.RegisterRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRegisterMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<AuthServiceOuterClass.BlacklisResponse> setBlacklist(
        AuthServiceOuterClass.BlacklistRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSetBlacklistMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<AuthServiceOuterClass.UpdatePasswordResponse> updatePassword(
        AuthServiceOuterClass.UpdatePasswordRequest request) {
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
          serviceImpl.auth((AuthServiceOuterClass.AuthRequest) request,
              (io.grpc.stub.StreamObserver<AuthServiceOuterClass.AuthResponse>) responseObserver);
          break;
        case METHODID_LOGOUT:
          serviceImpl.logout((AuthServiceOuterClass.LogoutRequest) request,
              (io.grpc.stub.StreamObserver<AuthServiceOuterClass.LogoutResponse>) responseObserver);
          break;
        case METHODID_VALIDATE:
          serviceImpl.validate((AuthServiceOuterClass.ValidateRequest) request,
              (io.grpc.stub.StreamObserver<AuthServiceOuterClass.ValidateResponse>) responseObserver);
          break;
        case METHODID_REGISTER:
          serviceImpl.register((AuthServiceOuterClass.RegisterRequest) request,
              (io.grpc.stub.StreamObserver<AuthServiceOuterClass.RegisterResponse>) responseObserver);
          break;
        case METHODID_SET_BLACKLIST:
          serviceImpl.setBlacklist((AuthServiceOuterClass.BlacklistRequest) request,
              (io.grpc.stub.StreamObserver<AuthServiceOuterClass.BlacklisResponse>) responseObserver);
          break;
        case METHODID_UPDATE_PASSWORD:
          serviceImpl.updatePassword((AuthServiceOuterClass.UpdatePasswordRequest) request,
              (io.grpc.stub.StreamObserver<AuthServiceOuterClass.UpdatePasswordResponse>) responseObserver);
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
      return AuthServiceOuterClass.getDescriptor();
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
