package com.example.proiect.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "Appointment")
@Table(name = "appointment")
@Getter
@Setter
public class Appointment {

    public Appointment() {
    }

    public Appointment(Patient patient, Doctor doctor, LocalDate date, String status) {
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

    @ManyToOne
    @JoinColumn(name = "id_doctor")
    @JsonIgnore
    private Doctor doctor;

    @Getter
    @Basic
    @Column ( name = "date" )
    private LocalDate date;

    @Getter
    @Basic
    @Column ( name = "status" )
    private String status;

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
