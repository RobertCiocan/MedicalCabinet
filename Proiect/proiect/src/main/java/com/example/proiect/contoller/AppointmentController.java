package com.example.proiect.contoller;

import com.example.proiect.model.Appointment;
import com.example.proiect.utils.CustomException;
import com.example.proiect.view.AppointmentService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public ResponseEntity<EntityModel<Appointment>> createAppointment(@RequestBody Appointment appointment) {
        System.out.println(appointment);
        try{
            Appointment createdAppointment = appointmentService.createAppointment(appointment);

            Link selfLink = linkTo(methodOn(AppointmentController.class).createAppointment(appointment)).withSelfRel();
            Link getLink = linkTo(methodOn(AppointmentController.class).getAppointment(createdAppointment.getId_appointment())).withRel("getAppointment").withType("GET");

            EntityModel<Appointment> resource = EntityModel.of(createdAppointment, selfLink, getLink);

            return ResponseEntity.status(HttpStatus.CREATED).body(resource);
        }catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();  // 409 Conflict
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // 500 Internal Server Error
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  // 400 Bad Request
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
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
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
    public ResponseEntity<EntityModel<Appointment>> updateAppointment(@PathVariable Long idAppointment, @RequestBody Appointment updatedAppointment) {
        Appointment appointment = appointmentService.updateAppointment(idAppointment, updatedAppointment);
        if (appointment != null) {
            Link selfLink = linkTo(methodOn(AppointmentController.class).updateAppointment(idAppointment, updatedAppointment)).withSelfRel();
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
