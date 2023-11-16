package com.example.proiect.contoller;

import com.example.proiect.view.DoctorService;
import com.example.proiect.model.Doctor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medical_office/Doctors")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("")
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        Doctor createdDoctor = doctorService.createDoctor(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDoctor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctor(@PathVariable Long id) {
        Optional<Doctor> doctor = doctorService.getDoctorById(id);
        return doctor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor updatedDoctor) {
        Doctor doctor = doctorService.updateDoctor(id, updatedDoctor);
        if (doctor != null) {
            return ResponseEntity.ok(doctor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
        boolean deleted = doctorService.deleteDoctor(id);
        if (deleted) {
            return ResponseEntity.ok("Doctor deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/searched")
    public List<Doctor> searchDoctors(
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) String name
    ) {
        if (specialization != null) {
            return doctorService.getDoctorsBySpecialization(specialization);
        } else if (name != null) {
            return doctorService.getDoctorsByName(name);
        } else {
            return doctorService.getAllDoctors();
        }
    }

    @GetMapping("/paginated")
    public ResponseEntity<List<Doctor>> getPaginatedDoctors(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "items_per_page", defaultValue = "10") int itemsPerPage
    ) {
        List<Doctor> paginated_doctors = doctorService.getDoctorsPages(page, itemsPerPage);

        if (paginated_doctors.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(paginated_doctors);
    }
}
