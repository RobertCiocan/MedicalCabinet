package com.example.proiect.view;

import com.example.proiect.model.Appointment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {
    private List<Appointment> appointments = new ArrayList<>();
    public Appointment createAppointment(Appointment appointment) {
        appointments.add(appointment);
        return appointment;
    }

    public List<Appointment> getAllAppointments() {
        return appointments;
    }

    public Appointment getAppointment(int idPatient, int idPhysician, LocalDate date) {
        return appointments.stream()
                .filter(appointment -> appointment.getId_patient() == idPatient &&
                        appointment.getId_physician() == idPhysician &&
                        appointment.getDate() == date)
                .findFirst()
                .orElse(null);
    }

    public Appointment updateAppointment(int idPatient, int idPhysician, LocalDate date, Appointment updatedAppointment) {
        Appointment existingAppointment = getAppointment(idPatient, idPhysician, date);
        if (existingAppointment != null) {
            existingAppointment.setDate(updatedAppointment.getDate());
            existingAppointment.setStatus(updatedAppointment.getStatus());
            return existingAppointment;
        }
        return null;
    }

    public boolean deleteAppointment(int idPatient, int idPhysician, LocalDate date) {
        Appointment appointment = getAppointment(idPatient, idPhysician, date);
        if (appointment != null) {
            appointments.remove(appointment);
            return true;
        }
        return false;
    }
}

