package com.example.proiect.repository;

import com.example.proiect.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query(value = "SELECT * FROM appointment WHERE id_doctor = :idDoctor AND date = :date", nativeQuery = true)
    Optional<List<Appointment>> findByDoctorIdAndDate(@Param("idDoctor") Long idDoctor, @Param("date") LocalDateTime date);

    @Query(value = "SELECT * FROM appointment WHERE id_doctor = :idDoctor", nativeQuery = true)
    Optional<List<Appointment>> findByDoctorId(@Param("idDoctor") Long idDoctor);

    @Query(value = "SELECT * FROM appointment WHERE id_patient = :idPatient", nativeQuery = true)
    Optional<List<Appointment>> findByPatientId(@Param("idPatient") Long idPatient);

    @Query(value = "SELECT * FROM appointment WHERE date = :date", nativeQuery = true)
    Optional<List<Appointment>> findByDate(@Param("date") LocalDateTime date);

    @Query(value = "SELECT * FROM appointment WHERE id_doctor = :idDoctor AND date BETWEEN :startDateTime AND :endDateTime", nativeQuery = true)
    Optional<List<Appointment>> findByDoctorIdAndDateBetween(
            @Param("idDoctor") Long idDoctor,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime
    );


    @Query(value = "SELECT * FROM appointment WHERE id_patient = :idPatient AND id_doctor = :idDoctor AND date BETWEEN :startOfDay AND :endOfDay", nativeQuery = true)
    Optional<List<Appointment>> findByPatientIdAndDoctorIdAndDate(
            @Param("idPatient") Long idPatient,
            @Param("idDoctor") Long idDoctor,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );

}
