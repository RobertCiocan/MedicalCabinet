package com.example.mongo_service.repository;

import com.example.mongo_service.model.Consultation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConsultationRepository extends MongoRepository<Consultation, String>{
}
