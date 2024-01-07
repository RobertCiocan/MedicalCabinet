package com.example.proiect.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Unique
    @Column(name = "email", unique = true)
    private String email;

    @Basic
    @Column(name = "phone_nr")
    private String phone_nr;

    @Basic
    @Column(name = "specialization")
    private String specialization;

    @OneToMany(mappedBy = "doctor")
    private Set<Appointment> appointments = new HashSet<>();

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