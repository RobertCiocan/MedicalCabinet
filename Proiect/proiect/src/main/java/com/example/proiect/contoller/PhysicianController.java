package com.example.proiect.contoller;

import com.example.proiect.view.PhysicianService;
import com.example.proiect.model.Physician;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/medical_office/physicians")
public class PhysicianController {
    private final PhysicianService physicianService;

    public PhysicianController(PhysicianService physicianService) {
        this.physicianService = physicianService;
    }

    @PostMapping
    public ResponseEntity<Physician> createPhysician(@RequestBody Physician physician) {
        Physician createdPhysician = physicianService.createPhysician(physician);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPhysician);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Physician> getPhysician(@PathVariable Long id) {
        Physician physician = physicianService.getPhysician(id);
        if (physician != null) {
            return ResponseEntity.ok(physician);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<Physician> getAllPhysicians() {
        return physicianService.getAllPhysicians();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Physician> updatePhysician(@PathVariable Long id, @RequestBody Physician updatedPhysician) {
        Physician physician = physicianService.updatePhysician(id, updatedPhysician);
        if (physician != null) {
            return ResponseEntity.ok(physician);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePhysician(@PathVariable Long id) {
        boolean deleted = physicianService.deletePhysician(id);
        if (deleted) {
            return ResponseEntity.ok("Physician deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<Physician> searchPhysicians(
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) String name
    ) {
        if (specialization != null) {
            return physicianService.getPhysiciansBySpecialization(specialization);
        } else if (name != null) {
            return physicianService.getPhysiciansByName(name);
        } else {
            return physicianService.getAllPhysicians();
        }
    }

    @GetMapping
    public ResponseEntity<List<Physician>> getPaginatedPhysicians(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "items_per_page", defaultValue = "10") int itemsPerPage
    ) {
        List<Physician> paginated_physicians = physicianService.getPhysiciansPages(page, itemsPerPage);

        if (paginated_physicians.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(paginated_physicians);
    }
}
