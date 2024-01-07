package com.example.mongo_service.controller;

import com.example.mongo_service.model.Consultation;
import com.example.mongo_service.model.Investigation;
import com.example.mongo_service.view.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/medical_office/consultations")
public class ConsultationController {
    private final ConsultationService consultationService;

    @Autowired
    public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @PostMapping("")
    public ResponseEntity<EntityModel<Consultation>> createConsultation(@RequestBody Consultation consultation) {
        try {
            System.out.println(consultation);
            Consultation createdConsultation = consultationService.createConsultation(consultation);

            Link selfLink = linkTo(methodOn(ConsultationController.class).createConsultation(createdConsultation)).withSelfRel();
            Link getLink = linkTo(methodOn(ConsultationController.class).getConsultation(null)).withRel("getConsultation").withType("GET");

            EntityModel<Consultation> resource = EntityModel.of(createdConsultation, selfLink, getLink);
            return ResponseEntity.status(HttpStatus.CREATED).body(resource);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 400 Bad Request
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 500 Internal Server Error
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Consultation>> getConsultation(@PathVariable String id) {
        try {
            Optional<Consultation> consultation = consultationService.getConsultationById(id);

            return consultation.map(c -> {
                Link selfLink = linkTo(methodOn(ConsultationController.class).getConsultation(id)).withSelfRel();
                Link updateLink = linkTo(methodOn(ConsultationController.class).updateConsultation(id, null)).withRel("updateConsultation").withType("PUT");
                Link deleteLink = linkTo(methodOn(ConsultationController.class).deleteConsultation(id)).withRel("deleteConsultation").withType("DELETE");

                EntityModel<Consultation> resource = EntityModel.of(c, selfLink, updateLink, deleteLink);
                return ResponseEntity.ok(resource);
            }).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 400 Bad Request
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 500 Internal Server Error
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Consultation>> updateConsultation(@PathVariable String id, @RequestBody Consultation updatedConsultation) {
        Consultation consultation = consultationService.updateConsultation(id, updatedConsultation);
        if (consultation != null) {
            Link selfLink = linkTo(methodOn(ConsultationController.class).updateConsultation(id, consultation)).withSelfRel();
            Link getLink = linkTo(methodOn(ConsultationController.class).getConsultation(null)).withRel("getConsultation").withType("GET");
            EntityModel<Consultation> resource = EntityModel.of(consultation, selfLink, getLink);
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntityModel<String>> deleteConsultation(@PathVariable String id) {
        boolean deleted = consultationService.deleteConsultation(id);
        if (deleted) {
            Link selfLink = linkTo(methodOn(ConsultationController.class).deleteConsultation(id)).withSelfRel();
            EntityModel<String> resource = EntityModel.of("Consultation deleted successfully.", selfLink);
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("")
    public ResponseEntity<List<EntityModel<Consultation>>> getAllConsultations() {
        try {
            List<Consultation> consultations = consultationService.getAllConsultations();

            if (consultations.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            List<EntityModel<Consultation>> consultationResources = consultations.stream()
                    .map(consultation -> EntityModel.of(consultation,
                            linkTo(methodOn(ConsultationController.class).getAllConsultations()).withSelfRel(),
                            linkTo(methodOn(ConsultationController.class).getConsultation(null)).withRel("getConsultation").withType("GET"),
                            linkTo(methodOn(ConsultationController.class).updateConsultation(null, null)).withRel("updateConsultation").withType("PUT"),
                            linkTo(methodOn(ConsultationController.class).deleteConsultation(null)).withRel("deleteConsultation").withType("DELETE")))
                    .toList();

            return ResponseEntity.ok(consultationResources);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 400 Bad Request
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 500 Internal Server Error
        }
    }

    @GetMapping("/{id}/investigations")
    public ResponseEntity<List<EntityModel<Investigation>>> getInvestigationsForConsultation(@PathVariable String id) {
        try {
            Optional<Consultation> consultation = consultationService.getConsultationById(id);

            if (consultation.isPresent()) {
                List<Investigation> investigations = consultation.get().getInvestigation();

                if (investigations.isEmpty()) {
                    return ResponseEntity.noContent().build();
                }

                List<EntityModel<Investigation>> investigationResources = investigations.stream()
                        .map(investigation -> EntityModel.of(investigation,
                                linkTo(methodOn(ConsultationController.class).getInvestigationsForConsultation(investigation.getName())).withSelfRel()
                        ))
                        .toList();

                return ResponseEntity.ok(investigationResources);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 400 Bad Request
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 500 Internal Server Error
        }
    }

}
