package com.example.proiect.contoller;

import com.example.proiect.model.Appointment;
import com.example.proiect.utils.CustomException;
import com.example.proiect.view.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.proiect.utils.ValidationUtil.createErrorMessage;
import static com.example.proiect.utils.ValidationUtil.getValidationErrors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/medical_office/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("")
    public ResponseEntity<?> createAppointment(@Valid @RequestBody Appointment appointment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(getValidationErrors(bindingResult));
        }
        try {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime appointmentDateTime = appointment.getDate();

            System.out.println("Appointment date: " + appointmentDateTime);

            if (appointmentDateTime.isBefore(now) || appointmentDateTime.isEqual(now)) {
                return ResponseEntity.badRequest().body(createErrorMessage("Appointment date must be in the future or within the next 15 minutes."));
            }

            if (appointmentService.hasAppointmentOnSameDay(appointment)) {
                return ResponseEntity.badRequest().body(createErrorMessage("Patient already has an appointment on the same day with the same doctor."));
            }

            if (appointmentService.hasOverlappingAppointments(appointment)) {
                return ResponseEntity.badRequest().body(createErrorMessage("Doctor has overlapping appointments."));
            }

            Appointment createdAppointment = appointmentService.createAppointment(appointment);

            Link selfLink = linkTo(methodOn(AppointmentController.class).createAppointment(appointment, null)).withSelfRel();
            Link getLink = linkTo(methodOn(AppointmentController.class).getAppointment(createdAppointment.getId_appointment())).withRel("getAppointment").withType("GET");

            EntityModel<Appointment> resource = EntityModel.of(createdAppointment, selfLink, getLink);

            return ResponseEntity.status(HttpStatus.CREATED).body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorMessage("Internal Server Error"));  // 500 Internal Server Error
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Appointment>> getAppointment(@PathVariable Long id) {
        try{
            Optional<Appointment> appointment = appointmentService.getAppointmentById(id);

            return appointment.map(a -> {
                Link selfLink = linkTo(methodOn(AppointmentController.class).getAppointment(id)).withSelfRel();
                EntityModel<Appointment> resource = EntityModel.of(a, selfLink);
                return ResponseEntity.ok(resource);
            }).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  // 400 Bad Request
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // 500 Internal Server Error
        }
    }

    @GetMapping("")
    public ResponseEntity<List<EntityModel<Appointment>>> getAppointmentByParam(
            @RequestParam(required = false) Long idPatient,
            @RequestParam(required = false) Long idDoctor,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date
    ) {
        try {
            List<Appointment> appointments = appointmentService.getAppointmentsByParams(idPatient, idDoctor, date);
            if (appointments != null) {
                List<EntityModel<Appointment>> resources = appointments.stream()
                        .map(a -> EntityModel.of(a, linkTo(methodOn(AppointmentController.class).getAppointment(a.getId_appointment())).withSelfRel()))
                        .toList();

                if (resources.isEmpty()) {
                    return ResponseEntity.notFound().build();
                }

                return ResponseEntity.ok().body(resources);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  // 400 Bad Request
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // 500 Internal Server Error
        }
    }

    @GetMapping("/all")
    public List<EntityModel<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return appointments.stream()
                .map(a -> EntityModel.of(a, linkTo(methodOn(AppointmentController.class).getAppointment(a.getId_appointment())).withSelfRel()))
                .collect(Collectors.toList());
    }

    @PutMapping("/{idAppointment}")
    public ResponseEntity<?> updateAppointment(@PathVariable Long idAppointment, @Valid @RequestBody Appointment updatedAppointment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(getValidationErrors(bindingResult));
        }
        Appointment appointment = appointmentService.updateAppointment(idAppointment, updatedAppointment);
        if (appointment != null) {
            Link selfLink = linkTo(methodOn(AppointmentController.class).updateAppointment(idAppointment, updatedAppointment, null)).withSelfRel();
            Link getLink = linkTo(methodOn(AppointmentController.class).getAppointment(idAppointment)).withRel("getAppointment").withType("GET");
            EntityModel<Appointment> resource = EntityModel.of(appointment, selfLink, getLink);
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idAppointment}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long idAppointment) {
        System.out.println("Deleting appointment with id: " + idAppointment);
        boolean deleted = appointmentService.deleteAppointment(idAppointment);

        if (deleted) {
            System.out.println("Appointment deleted successfully.");
            String jsonResponse = "{\"message\": \"Appointment deleted successfully.\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"Appointment not found.\"}");
        }
    }

}
