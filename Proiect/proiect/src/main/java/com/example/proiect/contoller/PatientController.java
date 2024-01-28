package com.example.proiect.contoller;

import com.example.proiect.model.Appointment;
import com.example.proiect.utils.CustomException;
import com.example.proiect.view.PatientService;
import com.example.proiect.model.Patient;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.proiect.utils.ValidationUtil.createErrorMessage;
import static com.example.proiect.utils.ValidationUtil.getValidationErrors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/medical_office/patients")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("")
    public ResponseEntity<?> createPatient(@Valid @RequestBody Patient patient, BindingResult bindingResult) {
        System.out.println("PatientController.createPatient");
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(getValidationErrors(bindingResult));
        }
        try {
            if(patientService.getPatientByCnp(patient.getCnp()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(createErrorMessage("Patient with the same CNP already exists."));
            }
            Patient createdPatient = patientService.createPatient(patient);
            Link selfLink = linkTo(methodOn(PatientController.class).createPatient(createdPatient, null)).withSelfRel();
            Link getLink = linkTo(methodOn(PatientController.class).getPatient(createdPatient.getId_patient())).withRel("getPatient").withType("GET");
            EntityModel<Patient> resource = EntityModel.of(createdPatient, selfLink, getLink);
            return ResponseEntity.status(HttpStatus.CREATED).body(resource);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(createErrorMessage("Data integrity violation."));
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorMessage(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage("Bad request."));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Patient>> getPatient(@PathVariable Long id) {
        try{
            Optional<Patient> patient = patientService.getPatientById(id);

            return patient.map(p -> {
                Link selfLink = linkTo(methodOn(PatientController.class).getPatient(id)).withSelfRel();
                Link updateLink = linkTo(methodOn(PatientController.class).updatePatient(id, null)).withRel("updatePatient").withType("PUT");
                Link deleteLink = linkTo(methodOn(PatientController.class).deletePatient(id)).withRel("deletePatient").withType("DELETE");
                Link searchLink = linkTo(methodOn(PatientController.class).getAppointmentsForPatient(id, null, null)).withRel("getAppointmentsForPatient").withType("GET");

                EntityModel<Patient> resource = EntityModel.of(p, selfLink, updateLink, deleteLink, searchLink);
                return ResponseEntity.ok(resource);
            }).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  // 400 Bad Request
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // 500 Internal Server Error
        }
    }

    @GetMapping("")
    public ResponseEntity<List<EntityModel<Patient>>> getAllPatients() {
        try{
            List<Patient> patients = patientService.getAllPatients();


            if (patients.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            List<EntityModel<Patient>> patientResources = patients.stream()
                    .map(p -> EntityModel.of(p,
                            linkTo(methodOn(PatientController.class).getAllPatients()).withSelfRel(),
                            linkTo(methodOn(PatientController.class).updatePatient(p.getId_patient(), null)).withRel("updatePatient").withType("PUT"),
                            linkTo(methodOn(PatientController.class).deletePatient(p.getId_patient())).withRel("deletePatient").withType("DELETE"),
                            linkTo(methodOn(PatientController.class).getAppointmentsForPatient(p.getId_patient(), null, null)).withRel("getAppointmentsForPatient").withType("GET")))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(patientResources);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  // 400 Bad Request
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // 500 Internal Server Error
        }
    }

    @GetMapping("/{id}/doctors")
    public ResponseEntity<List<EntityModel<Appointment>>> getAppointmentsForPatient(@PathVariable Long id) {
        try{
            Set<Appointment> appointments = patientService.getAppointmentsForPatient(id);

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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  // 400 Bad Request
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // 500 Internal Server Error
        }
    }

//    @GetMapping("/getBy")
//    public ResponseEntity<List<EntityModel<Appointment>>> getAppointmentsForPatient(
//            @RequestParam(required = false, name = "patientId") Long patientId,
//            @RequestParam(required = false, name = "userId") Long userId) {
//        try {
//            if (patientId == null && userId == null) {
//                return ResponseEntity.badRequest().build();
//            }
//
//            Set<Appointment> appointments;
//            if (patientId != null) {
//                appointments = patientService.getAppointmentsForPatient(patientId);
//            } else {
//                appointments = patientService.getAppointmentsForUser(userId);
//            }
//
//            if (appointments.isEmpty()) {
//                return ResponseEntity.noContent().build();
//            }
//
//            List<EntityModel<Appointment>> appointmentResources = appointments.stream()
//                    .map(appointment -> EntityModel.of(appointment,
//                            linkTo(methodOn(PatientController.class).getAppointmentsForPatient(patientId, null)).withSelfRel(),
//                            linkTo(methodOn(AppointmentController.class).getAppointment(appointment.getId_appointment())).withRel("getAppointment").withType("GET")))
//                    .collect(Collectors.toList());
//
//            return ResponseEntity.ok(appointmentResources);
//        } catch (IllegalArgumentException ex) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  // 400 Bad Request
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // 500 Internal Server Error
//        }
//    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable Long id, @RequestBody Patient updatedPatient) {
        Optional<Patient> patient = patientService.getPatientById(id);
        if (patient.isPresent()) {
            Patient updated = patientService.updatePatient(id, updatedPatient);
            Link selfLink = linkTo(methodOn(PatientController.class).updatePatient(id, updated)).withSelfRel();
            EntityModel<Patient> resource = EntityModel.of(updated, selfLink);
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntityModel<String>> deletePatient(@PathVariable Long id) {
        if (patientService.deletePatient(id)) {
            Link selfLink = linkTo(methodOn(PatientController.class).deletePatient(id)).withSelfRel();
            Link getLink = linkTo(methodOn(PatientController.class).getPatient(id)).withRel("getPatient").withType("GET");
            EntityModel<String> resource = EntityModel.of("Patient deleted successfully.", selfLink, getLink);
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/{id}", params = {"date", "type"} )
    public ResponseEntity<List<EntityModel<Appointment>>> getAppointmentsForPatient(
            @PathVariable Long id,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String type) {
        try {
            Set<Appointment> appointments = patientService.getAppointmentsByDateAndType(id, date, type);

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
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  // 400 Bad Request
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // 500 Internal Server Error
        }
    }

    @GetMapping(value="/patientId", produces="application/json")
    public ResponseEntity<String> getPatientIdFromUserId(@RequestParam Long userId) {
        System.out.println("PatientController.getPatientIdFromUserId");
        try {
            Long patientId = patientService.getPatientIdByUserId(userId);
            System.out.println(patientId);

            if (patientId != null) {
                String jsonResponse = String.format("{\"uid\": %d}", patientId);
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
