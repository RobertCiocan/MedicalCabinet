package com.example.proiect.view;

import com.example.proiect.model.Appointment;
import com.example.proiect.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public Appointment createAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
        return appointment;
    }

    public Appointment getAppointmentByParams(Long id, int idPhysician, LocalDate date) {

        //TODO
        return null;
    }


    public Appointment updateAppointment(Long id, Optional<Appointment> updatedAppointment) {

        Optional<Appointment> existingAppointment = getAppointmentById(id);
        if (existingAppointment.isPresent() && updatedAppointment.isPresent()) {
            existingAppointment.get().setDate(updatedAppointment.get().getDate());
            existingAppointment.get().setStatus(updatedAppointment.get().getStatus());

            appointmentRepository.save(existingAppointment.get());

            return existingAppointment.get();
        }
        return null;
    }

    public boolean deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
        return getAppointmentById(id).isPresent();
    }
}

