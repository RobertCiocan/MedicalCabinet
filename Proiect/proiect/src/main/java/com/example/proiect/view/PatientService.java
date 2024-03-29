package com.example.proiect.view;

import com.example.proiect.model.Appointment;
import com.example.proiect.model.Doctor;
import com.example.proiect.model.Patient;
import com.example.proiect.repository.AppointmentRepository;
import com.example.proiect.repository.PatientRepository;
import com.example.proiect.utils.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    public Optional<Patient> getPatientByCnp(String cnp) {
        return patientRepository.findByCnp(cnp);
    }

    public Patient createPatient(Patient patient) {
        patient.setBirth_date(getBirthDateFromCnp(patient.getCnp()));
        patient.set_active(true);
        patientRepository.save(patient);
        return patient;
    }

    public Patient updatePatient(Long id, Patient updatedPatient) {
        Optional<Patient> existingPatient = getPatientById(id);
        if (existingPatient.isPresent()) {
            Patient patientToUpdate = existingPatient.get();
            BeanUtils.copyProperties(updatedPatient, patientToUpdate, BeanUtil.getNullPropertyNames(updatedPatient));

            patientRepository.save(patientToUpdate);

            return patientToUpdate;
        }
        return null;
    }

    public boolean deletePatient(Long id) {
        patientRepository.deleteById(id);
        return getPatientById(id).isEmpty();
    }

    public Set<Appointment> getAppointmentsByDateAndType(Long id, String date, String type) {
        LocalDate localDate;
        Optional<Patient> current_patient = patientRepository.findById(id);

        try {
            if ("month".equalsIgnoreCase(type)) {
                localDate = LocalDate.now().withMonth(Integer.parseInt(date));
            } else if ("day".equalsIgnoreCase(type)) {
                localDate = LocalDate.now().withDayOfMonth(Integer.parseInt(date));
            } else {
                localDate = LocalDate.parse(date);
            }
        } catch (Exception e) {
            return new HashSet<>();
        }

        if(current_patient.isPresent()) {
            Set<Appointment> result = new HashSet<>();
            for (Appointment appointment : current_patient.get().getAppointments()) {
                if (appointment.getDate().getMonthValue() == localDate.getMonthValue() &&
                        appointment.getDate().getYear() == localDate.getYear() &&
                        ("day".equalsIgnoreCase(type) || appointment.getDate().getDayOfMonth() == localDate.getDayOfMonth())) {
                    result.add(appointment);
                }
            }
            return result;
        }

        return new HashSet<>();
    }

    public Set<Appointment> getAppointmentsForPatient(Long id) {
        if(getPatientById(id).isPresent())
            return getPatientById(id).get().getAppointments();
        return new HashSet<>();
    }

    public Set<Appointment> getAppointmentsForUser(Long userId) {
        Set<Appointment> appointments = new HashSet<>();
        for (Patient patient : patientRepository.findAll()) {
            if (patient.getId_user().equals(userId)) {
                appointments.addAll(patient.getAppointments());
            }
        }
        return appointments;
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
            return null;
        }
    }

    public Long getPatientIdByUserId(Long userId) {
        for (Patient patient : patientRepository.findAll()) {
            if (patient.getId_user().equals(userId)) {
                return patient.getId_patient();
            }
        }
        return null;
    }
}

