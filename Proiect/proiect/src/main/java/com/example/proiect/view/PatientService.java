package com.example.proiect.view;

import com.example.proiect.model.Appointment;
import com.example.proiect.model.Patient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService{
    private final List<Patient> patients = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();
    private Long patientIdCounter = 1L;

    public List<Patient> getAllPatients() {
        return patients;
    }

    public Patient getPatientById(Long id) {
        return findPatientById(id);
    }

    public Appointment addAppointment(Appointment appointment) {
        appointments.add(appointment);
        return appointment;
    }
    public Patient createPatient(Patient patient) {
        patient.setId_user(patientIdCounter++);

        patient.setBirth_date(getBirthDateFromCnp(patient.getCnp()));

        patients.add(patient);
        return patient;
    }

    public Patient updatePatient(Long id, Patient updatedPatient) {
        Patient existingPatient = findPatientById(id);
        if (existingPatient != null) {
            existingPatient.setEmail(updatedPatient.getEmail());
            existingPatient.setPhone_nr(updatedPatient.getPhone_nr());
            return existingPatient;
        }
        return null;
    }

    public boolean deletePatient(Long id) {
        Patient patient = findPatientById(id);
        if (patient != null) {
            patients.remove(patient);
            return true;
        }
        return false;
    }

    public Patient findPatientById(Long id) {
        return patients.stream()
                .filter(patient -> patient.getId_user().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Appointment> getAppointmentsByDateAndType(Long patientId, String date, String type) {
        LocalDate localDate;
        try {
            if ("month".equalsIgnoreCase(type)) {
                localDate = LocalDate.now().withMonth(Integer.parseInt(date));
            } else if ("day".equalsIgnoreCase(type)) {
                localDate = LocalDate.now().withDayOfMonth(Integer.parseInt(date));
            } else {
                localDate = LocalDate.parse(date);
            }
        } catch (Exception e) {
            return new ArrayList<>();
        }

        return appointments.stream()
                .filter(appointment -> appointment.getId_patient().equals(patientId) &&
                        appointment.getDate().getMonthValue() == localDate.getMonthValue() &&
                        appointment.getDate().getYear() == localDate.getYear() &&
                        ("day".equalsIgnoreCase(type) || appointment.getDate().getDayOfMonth() == localDate.getDayOfMonth()))
                .collect(Collectors.toList());
    }

    public List<Appointment> getAppointmentsForPatient(Long patientId) {
        return appointments.stream()
                .filter(appointment -> appointment.getId_patient().equals(patientId))
                .collect(Collectors.toList());
    }

    public LocalDate getBirthDateFromCnp(String cnp){
        int year = Integer.parseInt(cnp.substring(1, 3));
        int month = Integer.parseInt(cnp.substring(3, 5));
        int day = Integer.parseInt(cnp.substring(5, 7));

        if (month > 0 && month <= 12 && day > 0 && day <= 31) {
            int century = Integer.parseInt(cnp.substring(0, 1));
            if (century == 1 || century == 2) {
                year += 1900;
            } else if (century == 5 || century == 6) {
                year += 2000;
            }

            return LocalDate.of(year, month, day);
        } else {
            throw new IllegalArgumentException("Birthdate is invalid");
        }
    }
}

