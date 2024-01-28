package com.example.proiect.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @Size(min = 13, max = 13, message = "CNP must have 13 digits")
    @Pattern(regexp = "\\d+", message = "CNP must contain only digits")
    private String cnp;

    @Column(name = "last_name")
    @NotBlank(message = "Last name must not be blank")
    private String last_name;

    @Column(name = "first_name")
    @NotBlank(message = "First name must not be blank")
    private String first_name;

    @Column(name = "email", unique = true)
    @Email(message = "Invalid email format")
    private String email;

    @Column(name = "phone_nr")
//    @Size(min = 10, max = 10, message = "Phone number must have 10 digits")
//    @Pattern(regexp = "\\d+", message = "Phone number must contain only digits")
    private String phone_nr;

    @Column(name = "birth_date")
    private LocalDate birth_date;

    @Column(name = "is_active")
    @NotNull(message = "is_Active must not be null")
    @Pattern(regexp = "true|false", message = "is_Active must be true or false")
    private String is_Active;

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
