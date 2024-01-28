package com.example.mongo_service.view;

import com.example.mongo_service.model.Consultation;
import com.example.mongo_service.model.Investigation;
import com.example.mongo_service.repository.ConsultationRepository;
import com.example.mongo_service.utils.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ConsultationService {
    private final ConsultationRepository consultationRepository;

    @Autowired
    public ConsultationService(ConsultationRepository consultationRepository) {
        this.consultationRepository = consultationRepository;
    }

    public List<Consultation> getAllConsultations() {
        return consultationRepository.findAll();
    }

    public Optional<Consultation> getConsultationById(String id) {
        return consultationRepository.findById(id);
    }

    public Consultation createConsultation(Consultation consultation) {
        consultationRepository.save(consultation);
        return consultation;
    }

    public Consultation updateConsultation(String id, Consultation updatedConsultation) {
        Optional<Consultation> existingConsultation = getConsultationById(id);
        if (existingConsultation.isPresent()) {
            Consultation consultationToUpdate = existingConsultation.get();

            BeanUtils.copyProperties(updatedConsultation, consultationToUpdate, BeanUtil.getNullPropertyNames(updatedConsultation));

            if (updatedConsultation.getInvestigation() != null) {
                if (consultationToUpdate.getInvestigation() == null) {
                    consultationToUpdate.setInvestigation(new ArrayList<>());
                }

                Set<String> existingInvestigationNames = consultationToUpdate.getInvestigation()
                        .stream()
                        .map(Investigation::getName)
                        .collect(Collectors.toSet());

                for (Investigation newInvestigation : updatedConsultation.getInvestigation()) {
                    if (!existingInvestigationNames.contains(newInvestigation.getName())) {
                        consultationToUpdate.addInvestigation(newInvestigation);
                    }
                }
            }

            consultationRepository.save(consultationToUpdate);

            return consultationToUpdate;
        }
        return null;
    }

    public Consultation addInvestigationToConsultation(String id, Investigation newInvestigation) {
        Optional<Consultation> existingConsultation = getConsultationById(id);

        if (existingConsultation.isPresent()) {
            Consultation consultationToUpdate = existingConsultation.get();

            consultationToUpdate.getInvestigation().add(newInvestigation);

            consultationRepository.save(consultationToUpdate);

            return consultationToUpdate;
        }

        return null;
    }

    public boolean deleteConsultation(String id) {
        Optional<Consultation> consultation = consultationRepository.findById(id);

        if (consultation.isPresent()) {
            consultationRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<Consultation> getConsultationsByPatientId(Long patientId) {
        return consultationRepository.findByPatientId(patientId).orElse(null);
    }

    public List<Consultation> getConsultationsByDoctorId(Long doctorId) {
        return consultationRepository.findByDoctorId(doctorId).orElse(null);
    }

}
