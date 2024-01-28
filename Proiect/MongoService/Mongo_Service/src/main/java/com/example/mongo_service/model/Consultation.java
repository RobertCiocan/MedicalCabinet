package com.example.mongo_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Data
@Document
public class Consultation {
    @MongoId
    private String id;
    @Indexed
    private Long id_patient;
    @Indexed
    private Long id_doctor;
    @Indexed
    private LocalDateTime date;
    private Diagnostic diagnostic;
    private List<Investigation> investigation;

    public Consultation(Long id_patient, Long id_doctor, LocalDateTime date, Diagnostic diagnostic, List<Investigation> investigation) {
        this.id_patient = id_patient;
        this.id_doctor = id_doctor;
        this.date = date;
        this.diagnostic = diagnostic;
        this.investigation = investigation;
    }
}
