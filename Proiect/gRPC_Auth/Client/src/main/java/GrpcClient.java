import com.proiect.proto.AuthServiceGrpc;
import com.proiect.proto.AuthServiceOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        AuthServiceGrpc.AuthServiceBlockingStub stub
                = AuthServiceGrpc.newBlockingStub(channel);

//        AuthServiceOuterClass.RegisterResponse registerResponse = stub.register(AuthServiceOuterClass.RegisterRequest.newBuilder()
//                .setUsername("cioocan")
//                .setPassword("parola")
//                .setRole("admin")
//                .build());

        AuthServiceOuterClass.AuthResponse authResponse = stub.auth(AuthServiceOuterClass.AuthRequest.newBuilder()
                .setUsername("cioocan")
                .setPassword("parola")
                .build());
//
        String token = authResponse.getToken();

        AuthServiceOuterClass.ValidateResponse validateResponse = stub.validate(AuthServiceOuterClass.ValidateRequest.newBuilder()
                        .setToken(token)
                        .build());
        System.out.println(authResponse.getToken());
        System.out.println(validateResponse.getIsValid());

//        AuthServiceOuterClass.BlacklisResponse blacklisResponse = stub.setBlacklist(AuthServiceOuterClass.BlacklistRequest.newBuilder()
//                        .setToken(authResponse.getToken())
//                        .build());
//        AuthServiceOuterClass.LogoutResponse logoutResponse = stub.logout(AuthServiceOuterClass.LogoutRequest.newBuilder()
//                        .setToken(authResponse.getToken())
//                        .build());
//
//        validateResponse = stub.validate(AuthServiceOuterClass.ValidateRequest.newBuilder()
//                .setToken(authResponse.getToken())
//                .build());
//        System.out.println(validateResponse.getIsValid());

//        AuthServiceOuterClass.UpdatePasswordResponse updatePasswordResponse = stub.updatePassword(AuthServiceOuterClass.UpdatePasswordRequest.newBuilder()
//                        .setUsername("cioocan")
//                        .setOldPassword("parola2")
//                        .setNewPassword("parola")
//                        .build());

        channel.shutdown();
    }
}