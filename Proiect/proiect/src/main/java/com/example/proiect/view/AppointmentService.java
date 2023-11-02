package com.example.proiect.view;

import com.example.proiect.model.Appointment;
import com.example.proiect.model.Patient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {
    private List<Appointment> appointments = new ArrayList<>();
    private Long appointmentsIdCounter = 1L;
    public Appointment createAppointment(Appointment appointment) {
        appointment.setId_appointment(appointmentsIdCounter++);

        appointments.add(appointment);
        return appointment;
    }

    public List<Appointment> getAllAppointments() {
        return appointments;
    }

    public Appointment getAppointment(Long idAppointment) {
        return appointments.stream()
                .filter(appointment -> appointment.getId_appointment() == idAppointment)
                .findFirst()
                .orElse(null);
    }

    public Appointment getAppointmentByParams(int idPatient, int idPhysician, LocalDate date) {
        return appointments.stream()
                .filter(appointment -> appointment.getId_patient() == idPatient &&
                        appointment.getId_physician() == idPhysician &&
                        appointment.getDate() == date)
                .findFirst()
                .orElse(null);
    }

    public Appointment updateAppointment(Long idAppointment, Appointment updatedAppointment) {
        Appointment existingAppointment = getAppointment(idAppointment);
        if (existingAppointment != null) {
            existingAppointment.setDate(updatedAppointment.getDate());
            existingAppointment.setStatus(updatedAppointment.getStatus());
            return existingAppointment;
        }
        return null;
    }

    public boolean deleteAppointment(Long idAppointment) {
        Appointment appointment = getAppointment(idAppointment);
        if (appointment != null) {
            appointments.remove(appointment);
            return true;
        }
        return false;
    }
}

