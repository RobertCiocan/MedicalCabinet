package com.example.proiect.contoller;

import com.example.proiect.model.Appointment;
import com.example.proiect.model.Patient;
import com.example.proiect.utils.CustomException;
import com.example.proiect.view.DoctorService;
import com.example.proiect.model.Doctor;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.proiect.utils.ValidationUtil.createErrorMessage;
import static com.example.proiect.utils.ValidationUtil.getValidationErrors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/medical_office/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("")
    public ResponseEntity<?> createDoctor(@Valid @RequestBody Doctor doctor, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(getValidationErrors(bindingResult));
        }

        try {
            Doctor createdDoctor = doctorService.createDoctor(doctor);
            Link selfLink = linkTo(methodOn(DoctorController.class).createDoctor(createdDoctor, null)).withSelfRel();
            Link getLink = linkTo(methodOn(DoctorController.class).getDoctor(createdDoctor.getId_doctor())).withRel("getDoctor").withType("GET");
            EntityModel<Doctor> resource = EntityModel.of(createdDoctor, selfLink, getLink);
            return ResponseEntity.status(HttpStatus.CREATED).body(resource); // 201 Created
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(createErrorMessage("Email already exists."));
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorMessage(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage("Error creating doctor."));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Doctor>> getDoctor(@PathVariable Long id) {
        try{
            Optional<Doctor> doctor = doctorService.getDoctorById(id);

            return doctor.map(d -> {
                Link selfLink = linkTo(methodOn(DoctorController.class).getDoctor(id)).withSelfRel();
                Link updateLink = linkTo(methodOn(DoctorController.class).updateDoctor(id, null, null)).withRel("updateDoctor").withType("PUT");
                Link deleteLink = linkTo(methodOn(DoctorController.class).deleteDoctor(id)).withRel("deleteDoctor").withType("DELETE");
                Link searchLink = linkTo(methodOn(DoctorController.class).getBy(null, null)).withRel("searchDoctors").withType("GET");

                EntityModel<Doctor> resource = EntityModel.of(d, selfLink, updateLink, deleteLink, searchLink);
                return ResponseEntity.ok(resource);
            }).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

// implementat in getBy fara param
//    @GetMapping("")
//    public List<EntityModel<Doctor>> getAllDoctors() {
//        List<Doctor> doctors = doctorService.getAllDoctors();
//        return doctors.stream()
//                .map(doctor -> EntityModel.of(doctor,
//                        linkTo(methodOn(DoctorController.class).getAllDoctors()).withSelfRel(),
//                        linkTo(methodOn(DoctorController.class).getDoctor(doctor.getId_doctor())).withRel("getDoctor").withType("GET"),
//                        linkTo(methodOn(DoctorController.class).updateDoctor(doctor.getId_doctor(), null)).withRel("updateDoctor").withType("PUT"),
//                        linkTo(methodOn(DoctorController.class).deleteDoctor(doctor.getId_doctor())).withRel("deleteDoctor").withType("DELETE")))
//                .collect(Collectors.toList());
//    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDoctor(@PathVariable Long id, @Valid @RequestBody Doctor updatedDoctor, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(getValidationErrors(bindingResult));
        }

        Doctor doctor = doctorService.updateDoctor(id, updatedDoctor);
        if (doctor != null) {
            Link selfLink = linkTo(methodOn(DoctorController.class).updateDoctor(id, doctor, null)).withSelfRel();
            Link getLink = linkTo(methodOn(DoctorController.class).getDoctor(doctor.getId_doctor())).withRel("getDoctor").withType("GET");
            EntityModel<Doctor> resource = EntityModel.of(doctor, selfLink, getLink);
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntityModel<String>> deleteDoctor(@PathVariable Long id) {
        boolean deleted = doctorService.deleteDoctor(id);
        if (deleted) {
            Link selfLink = linkTo(methodOn(DoctorController.class).deleteDoctor(id)).withSelfRel();
            Link getLink = linkTo(methodOn(DoctorController.class).getDoctor(id)).withRel("getDoctor").withType("GET");
            EntityModel<String> resource = EntityModel.of("Doctor deleted successfully.", selfLink, getLink);
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // TODO: add more links to appointments
    @GetMapping("/{id}/patients")
    public ResponseEntity<List<EntityModel<Appointment>>> getAppointmentsForDoctor(@PathVariable Long id) {
        try{
            Set<Appointment> appointments = doctorService.getAppointmentsForDoctor(id);

            if (appointments.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            List<EntityModel<Appointment>> appointmentResources = appointments.stream()
                    .map(appointment -> EntityModel.of(appointment,
                            linkTo(methodOn(PatientController.class).getAppointmentsForPatient(null, null, null)).withSelfRel(),
                            linkTo(methodOn(AppointmentController.class).getAppointment(appointment.getId_appointment())).withRel("getAppointment").withType("GET")))
                    .collect(Collectors.toList());

        return ResponseEntity.ok(appointmentResources);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 Bad Request
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

    @GetMapping("")
    public ResponseEntity<List<EntityModel<Doctor>>> getBy(
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) String name
    ) {
        try{
            List<Doctor> searchResults;
            if (specialization != null) {
                searchResults = doctorService.getDoctorsBySpecialization(specialization);
            } else if (name != null) {
                searchResults = doctorService.getDoctorsByName(name);
            } else {
                searchResults = doctorService.getAllDoctors();
            }

            if (searchResults.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            List<EntityModel<Doctor>> doctorResources = searchResults.stream()
                .map(doctor -> EntityModel.of(doctor,
                        linkTo(methodOn(DoctorController.class).getBy(null, null)).withSelfRel(),
                        linkTo(methodOn(DoctorController.class).getDoctor(doctor.getId_doctor())).withRel("getDoctor").withType("GET"),
                        linkTo(methodOn(DoctorController.class).updateDoctor(doctor.getId_doctor(), null, null)).withRel("updateDoctor").withType("PUT"),
                        linkTo(methodOn(DoctorController.class).deleteDoctor(doctor.getId_doctor())).withRel("deleteDoctor").withType("DELETE")))
                .toList();
            return ResponseEntity.ok(doctorResources);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 Bad Request
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // 500 Internal Server Error
        }
    }

    @GetMapping(params = {"page", "items_per_page"})
    public ResponseEntity<List<EntityModel<Doctor>>> getPaginatedDoctors(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "items_per_page", defaultValue = "5") int itemsPerPage
    ) {
        try {
            Page<Doctor> paginatedDoctors = doctorService.getDoctorsPages(page, itemsPerPage);

            if (paginatedDoctors.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            List<EntityModel<Doctor>> paginatedDoctorResources = paginatedDoctors.stream()
                    .map(doctor -> EntityModel.of(doctor,
                            linkTo(methodOn(DoctorController.class).getPaginatedDoctors(page, itemsPerPage)).withSelfRel(),
                            linkTo(methodOn(DoctorController.class).getDoctor(doctor.getId_doctor())).withRel("getDoctor").withType("GET"),
                            linkTo(methodOn(DoctorController.class).updateDoctor(doctor.getId_doctor(), null, null)).withRel("updateDoctor").withType("PUT"),
                            linkTo(methodOn(DoctorController.class).deleteDoctor(doctor.getId_doctor())).withRel("deleteDoctor").withType("DELETE")))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(paginatedDoctorResources);

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  // 400 Bad Request
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // 500 Internal Server Error
        }
    }

    @GetMapping(value="/doctorId", produces="application/json")
    public ResponseEntity<String> getPatientIdFromUserId(@RequestParam Long userId) {
        try {
            Long doctorId = doctorService.getDoctorIdIdByUserId(userId);

            if (doctorId != null) {
                String jsonResponse = String.format("{\"uid\": %d}", doctorId);
                return ResponseEntity.ok(jsonResponse);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  // 400 Bad Request
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // 500 Internal Server Error
        }
    }
}
