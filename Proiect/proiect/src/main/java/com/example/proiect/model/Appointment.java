package com.example.proiect.model;

import java.time.LocalDate;

public class Appointment {
    private Long id_patient;
    private int id_physician;
    private LocalDate date;
    private String status;

    public Long getId_patient() {
        return id_patient;
    }

    public void setId_patient(Long id_patient) {
        this.id_patient = id_patient;
    }

    public int getId_physician() {
        return id_physician;
    }

    public void setId_physician(int id_physician) {
        this.id_physician = id_physician;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}