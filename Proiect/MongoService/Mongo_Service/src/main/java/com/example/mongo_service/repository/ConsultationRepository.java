package com.example.mongo_service.repository;

import com.example.mongo_service.model.Consultation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConsultationRepository extends MongoRepository<Consultation, String>{
    @Query("{ 'id_doctor' : ?0 }")
    Optional<List<Consultation>> findByDoctorId(Long doctorId);
    @Query("{ 'id_patient' : ?0 }")
    Optional<List<Consultation>> findByPatientId(Long patientId);
}
