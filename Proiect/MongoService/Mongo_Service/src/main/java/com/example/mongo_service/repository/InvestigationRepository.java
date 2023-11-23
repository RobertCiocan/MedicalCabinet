package com.example.mongo_service.repository;

import com.example.mongo_service.model.Investigation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvestigationRepository extends MongoRepository<Investigation, Long> {
}
