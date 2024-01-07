package com.proiect;

import com.proiect.utils.HibernateUtil;
import com.proiect.service.AuthImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.hibernate.SessionFactory;


public class GrpcServer{

    public static void main(String[] args) throws Exception {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        AuthImpl authService = new AuthImpl(sessionFactory);

        Server server = ServerBuilder.forPort(50051)
                .addService(authService)
                .build();

        server.start();
        server.awaitTermination();
    }
}
