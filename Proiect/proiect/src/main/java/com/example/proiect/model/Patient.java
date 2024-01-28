package com.example.proiect.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "Patient")
@Table(name = "patient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_patient")
    private Long id_patient;

    @Column(name = "id_user")
    private Long id_user;

    @Column(name = "cnp")
    private String cnp;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_nr")
    private String phone_nr;

    @Column(name = "birth_date")
    private LocalDate birth_date;

    @Column(name = "is_active")
    private boolean is_active;

    @OneToMany(mappedBy = "patient")
    private Set<Appointment> appointments = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        return id_patient != null && id_patient.equals(((Patient) o).getId_patient());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
