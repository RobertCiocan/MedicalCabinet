package com.example.proiect.contoller;

import com.example.proiect.model.Appointment;
import com.example.proiect.view.PatientService;
import com.example.proiect.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/medical_office/patients")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        Patient createdPatient = patientService.createPatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatient(@PathVariable Long id) {
        Patient patient = patientService.findPatientById(id);
        if (patient != null) {
            return ResponseEntity.ok(patient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient updatedPatient) {
        Patient patient = patientService.findPatientById(id);
        if (patient != null) {
            Patient updated = patientService.updatePatient(id, updatedPatient);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        if (patientService.deletePatient(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/appointments")
    public ResponseEntity<Appointment> createAppointmentForPatient(
            @PathVariable Long id,
            @RequestBody Appointment appointment) {
        appointment.setId_patient(id);

        Appointment createdAppointment = patientService.addAppointment(appointment);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
    }

    @GetMapping("/{id}")
    public List<Appointment> getAppointmentsForPatient(
            @PathVariable Long id,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String type) {
        if (date != null && type != null) {
            return patientService.getAppointmentsByDateAndType(id, date, type);
        } else {
            return patientService.getAppointmentsForPatient(id);
        }
    }
}

