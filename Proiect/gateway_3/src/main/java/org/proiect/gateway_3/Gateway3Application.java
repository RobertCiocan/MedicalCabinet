package org.proiect.gateway_3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class Gateway3Application {

    public static void main(String[] args) {
        SpringApplication.run(Gateway3Application.class, args);
    }

}
