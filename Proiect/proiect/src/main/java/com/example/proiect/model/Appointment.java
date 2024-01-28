package com.example.proiect.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "Appointment")
@Table(name = "appointment")
@Getter
@Setter
public class Appointment {

    public Appointment() {
    }

    public Appointment(Patient patient, Doctor doctor, LocalDateTime date, String status) {
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.status = status;
    }

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column ( name = "id_appointment" )
    private Long id_appointment;

    @ManyToOne
    @JoinColumn(name = "id_patient")
    private Patient patient;

    @Setter
    @ManyToOne
    @JoinColumn(name = "id_doctor")
    @JsonIgnore
    private Doctor doctor;

    @Getter
    @Basic
    @Column(name = "date")
    @JsonFormat(pattern = "dd:MM:yyyy HH:mm")
    private LocalDateTime date;

    @Getter
    @Basic
    @Column ( name = "status" )
    private String status;

    @Getter
    @Basic
    @Column ( name = "id_doc" )
    private Long id_doc;

    @Getter
    @Basic
    @Column ( name = "id_pat" )
    private Long id_pat;

    @JsonBackReference
    public Patient getPatient() {
        return patient;
    }

    @JsonBackReference
    public Doctor getDoctor() {
        return doctor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment)) return false;
        return id_appointment != null && id_appointment.equals(((Appointment) o).getId_appointment());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
