package com.example.proiect.view;

import com.example.proiect.model.Appointment;
import com.example.proiect.repository.AppointmentRepository;
import com.example.proiect.repository.DoctorRepository;
import com.example.proiect.utils.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public Appointment createAppointment(Appointment appointment) {
        appointment.setDoctor(doctorRepository.findById(appointment.getId_doc()).orElse(null));
        if (appointment.getDoctor() == null) {
            return null;
        }
        appointmentRepository.save(appointment);
        return appointment;
    }

    public List<Appointment> getAppointmentsByParams(Long patientId, Long doctorId, LocalDateTime date) {
        if (patientId != null) {
            return appointmentRepository.findByPatientId(patientId).orElse(null);
        } else if (doctorId != null && date != null) {
            return appointmentRepository.findByDoctorIdAndDate(doctorId, date).orElse(null);
        } else if (doctorId != null) {
            return appointmentRepository.findByDoctorId(doctorId).orElse(null);
        } else if (date != null) {
            return appointmentRepository.findByDate(date).orElse(null);
        } else {
            return appointmentRepository.findAll();
        }
    }


    public Appointment updateAppointment(Long id, Appointment updatedAppointment) {
        Optional<Appointment> existingAppointment = getAppointmentById(id);
        if (existingAppointment.isPresent()) {
            Appointment appointmentToUpdate = existingAppointment.get();

            updatedAppointment.setDate(updatedAppointment.getDate());

            BeanUtils.copyProperties(updatedAppointment, appointmentToUpdate, BeanUtil.getNullPropertyNames(updatedAppointment));

            appointmentRepository.save(appointmentToUpdate);

            return appointmentToUpdate;
        }
        return null;
    }

    public boolean deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
        return getAppointmentById(id).isEmpty();
    }

    public boolean hasAppointmentOnSameDay(Appointment appointment) {
        Long patientId = appointment.getPatient().getId_patient();
        Long doctorId = appointment.getId_doc();
        LocalDateTime appointmentDate = appointment.getDate();

        LocalDateTime startOfDay = appointmentDate.toLocalDate().atStartOfDay();

        LocalDateTime endOfDay = startOfDay.plusDays(1);

        List<Appointment> sameDayAppointments = appointmentRepository.findByPatientIdAndDoctorIdAndDate(
                patientId,
                doctorId,
                startOfDay,
                endOfDay
        ).orElse(null);

        return sameDayAppointments != null && !sameDayAppointments.isEmpty();
    }

    public boolean hasOverlappingAppointments(Appointment appointment) {
        Long doctorId = appointment.getId_doc();
        LocalDateTime appointmentStart = appointment.getDate().minusMinutes(15);
        LocalDateTime appointmentEnd = appointmentStart.plusMinutes(15);

        List<Appointment> overlappingAppointments = appointmentRepository.findByDoctorIdAndDateBetween(
                doctorId,
                appointmentStart,
                appointmentEnd
        ).orElse(null);

        return overlappingAppointments != null && !overlappingAppointments.isEmpty();
    }

}
