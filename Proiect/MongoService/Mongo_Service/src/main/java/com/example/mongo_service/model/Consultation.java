package com.example.mongo_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Data
@Document
public class Consultation {
    @Id
    private Long id_consultation;
    private Long id_patient;
    private Long id_doctor;
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
