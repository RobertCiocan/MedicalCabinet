package com.example.mongo_service.view;

import com.example.mongo_service.model.Consultation;
import com.example.mongo_service.repository.ConsultationRepository;
import com.example.mongo_service.utils.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

            // dupa ce am consultat documentatia am vazut ca exista deja BeanUtils.copyProperties
            BeanUtils.copyProperties(updatedConsultation, consultationToUpdate, BeanUtil.getNullPropertyNames(updatedConsultation));

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
