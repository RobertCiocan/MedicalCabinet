package com.example.proiect.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Doctor")
@Table(name = "doctor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_doctor")
    private Long id_doctor;

    @Basic
    @Column(name = "id_user")
    private Long id_user;

    @Basic
    @Column(name = "last_name")
    private String last_name;

    @Basic
    @Column(name = "first_name")
    private String first_name;

    @Basic
    @Column(name = "email")
    private String email;

    @Basic
    @Column(name = "phone_nr")
    private String phone_nr;

    @Basic
    @Column(name = "specialization")
    private String specialization;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor)) return false;
        return id_doctor != null && id_doctor.equals(((Doctor) o).getId_doctor());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}