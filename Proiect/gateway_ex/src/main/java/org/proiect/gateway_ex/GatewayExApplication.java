package org.proiect.gateway_ex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class GatewayExApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayExApplication.class, args);
	}

}
