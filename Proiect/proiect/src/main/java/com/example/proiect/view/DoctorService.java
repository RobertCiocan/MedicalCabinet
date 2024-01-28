package com.example.proiect.view;

import com.example.proiect.model.Appointment;
import com.example.proiect.model.Doctor;
import com.example.proiect.model.Patient;
import com.example.proiect.repository.AppointmentRepository;
import com.example.proiect.repository.DoctorRepository;
import com.example.proiect.utils.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
            Doctor doctorToUpdate = existingDoctor.get();

            BeanUtils.copyProperties(updatedDoctor, doctorToUpdate, BeanUtil.getNullPropertyNames(updatedDoctor));

            doctorRepository.save(doctorToUpdate);

            return doctorToUpdate;
        }
        return null;
    }

    public boolean deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
        return getDoctorById(id).isEmpty();
    }

    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return getAllDoctors().stream()
                .filter(doctor -> doctor.getSpecialization().toLowerCase().contains(specialization.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Doctor> getDoctorsByName(String name) {
        return getAllDoctors().stream()
                .filter(doctor ->
                        doctor.getLast_name().toLowerCase().startsWith(name.toLowerCase()) ||
                                doctor.getFirst_name().toLowerCase().startsWith(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Page<Doctor> getDoctorsPages(int page, int itemsPerPage) {
        PageRequest pageRequest = PageRequest.of(page - 1, itemsPerPage);
        return doctorRepository.findAll(pageRequest);
    }

    public Set<Appointment> getAppointmentsForDoctor(Long doctorId) {
        if(getDoctorById(doctorId).isPresent())
            return getDoctorById(doctorId).get().getAppointments();
        return new HashSet<Appointment>();
    }

    public Long getDoctorIdIdByUserId(Long userId) {
        for (Doctor doctor : doctorRepository.findAll()) {
            if (doctor.getId_user().equals(userId)) {
                return doctor.getId_doctor();
            }
        }
        return null;
    }
}

