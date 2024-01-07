package com.example.proiect.repository;

import com.example.proiect.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query(value = "SELECT * FROM appointment WHERE id_doctor = :doctorId", nativeQuery = true)
    List<Appointment> findAppointmentsByDoctorId(@Param("doctorId") Long doctorId);
    //List<Appointment> findByPatientId_patient(Long doctorId);
}
