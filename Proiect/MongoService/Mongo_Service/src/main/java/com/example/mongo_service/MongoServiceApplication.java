package com.example.mongo_service;

import com.example.mongo_service.repository.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MongoServiceApplication implements CommandLineRunner{

	@Autowired
	public ConsultationRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(MongoServiceApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		System.out.println("awd");
	}
}
