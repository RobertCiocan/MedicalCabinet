package com.example.proiect.contoller;

import com.example.proiect.model.Appointment;
import com.example.proiect.view.AppointmentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/medical_office/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        Appointment createdAppointment = appointmentService.createAppointment(appointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
    }

    @GetMapping("/{idPatient}/{idPhysician}/{date}")
    public ResponseEntity<Appointment> getAppointment(
            @PathVariable int idPatient, @PathVariable int idPhysician, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Appointment appointment = appointmentService.getAppointment(idPatient, idPhysician, date);
        if (appointment != null) {
            return ResponseEntity.ok(appointment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @PutMapping("/{idPatient}/{idPhysician}/{date}")
    public ResponseEntity<Appointment> updateAppointment(
            @PathVariable int idPatient, @PathVariable int idPhysician, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestBody Appointment updatedAppointment) {
        Appointment appointment = appointmentService.updateAppointment(idPatient, idPhysician, date, updatedAppointment);
        if (appointment != null) {
            return ResponseEntity.ok(appointment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idPatient}/{idPhysician}/{date}")
    public ResponseEntity<String> deleteAppointment(
            @PathVariable int idPatient, @PathVariable int idPhysician, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        boolean deleted = appointmentService.deleteAppointment(idPatient, idPhysician, date);
        if (deleted) {
            return ResponseEntity.ok("Appointment deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

