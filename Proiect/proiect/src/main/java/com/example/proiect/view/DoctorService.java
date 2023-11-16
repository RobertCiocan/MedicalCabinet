package com.example.proiect.view;

import com.example.proiect.model.Doctor;
import com.example.proiect.model.Patient;
import com.example.proiect.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    public Doctor createDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
        return doctor;
    }

    public Doctor updateDoctor(Long id, Doctor updatedDoctor) {
        Optional<Doctor> existingDoctor = getDoctorById(id);
        if (existingDoctor.isPresent()) {
            existingDoctor.get().setEmail(updatedDoctor.getEmail());
            existingDoctor.get().setPhone_nr(updatedDoctor.getPhone_nr());

            doctorRepository.save(existingDoctor.get());

            return existingDoctor.get();
        }
        return null;
    }

    public boolean deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
        return getDoctorById(id).isPresent();
    }

    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return getAllDoctors().stream()
                .filter(doctor -> doctor.getSpecialization().equalsIgnoreCase(specialization))
                .collect(Collectors.toList());
    }

    public List<Doctor> getDoctorsByName(String name) {
        return getAllDoctors().stream()
                .filter(doctor -> doctor.getLast_name().startsWith(name) || doctor.getFirst_name().startsWith(name))
                .collect(Collectors.toList());
    }


    // TODO - mergea doar pt liste
    public List<Doctor> getDoctorsPages(int page, int itemsPerPage) {
        int startIndex = (page - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, getAllDoctors().size());

        if (startIndex < endIndex) {
            return getAllDoctors().subList(startIndex, endIndex);
        }

        return Collections.emptyList();
    }
}

