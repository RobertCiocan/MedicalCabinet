package com.example.proiect.view;

import com.example.proiect.model.Physician;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PhysicianService {
    private List<Physician> physicians = new ArrayList<>();
    private Long physicianIdCounter = 1L;

    public Physician createPhysician(Physician physician) {
        physician.setId_physician(physicianIdCounter++);
        physicians.add(physician);
        return physician;
    }

    public List<Physician> getAllPhysicians() {
        return physicians;
    }

    public Physician getPhysician(Long id) {
        return physicians.stream()
                .filter(physician -> Objects.equals(physician.getId_physician(), id))
                .findFirst()
                .orElse(null);
    }

    public Physician updatePhysician(Long id, Physician updatedPhysician) {
        Physician existingPhysician = getPhysician(id);
        if (existingPhysician != null) {
            existingPhysician.setEmail(updatedPhysician.getEmail());
            existingPhysician.setPhone_nr(updatedPhysician.getPhone_nr());
            existingPhysician.setSpecialization(updatedPhysician.getSpecialization());
            return existingPhysician;
        }
        return null;
    }

    public boolean deletePhysician(Long id) {
        Physician physician = getPhysician(id);
        if (physician != null) {
            physicians.remove(physician);
            return true;
        }
        return false;
    }

    public List<Physician> getPhysiciansBySpecialization(String specialization) {
        return physicians.stream()
                .filter(physician -> physician.getSpecialization().equalsIgnoreCase(specialization))
                .collect(Collectors.toList());
    }

    public List<Physician> getPhysiciansPages(int page, int itemsPerPage) {
        int startIndex = (page - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, physicians.size());

        if (startIndex < endIndex) {
            return physicians.subList(startIndex, endIndex);
        }

        return Collections.emptyList();
    }

    public List<Physician> getPhysiciansByName(String name) {
        return physicians.stream()
                .filter(physician -> physician.getLast_name().startsWith(name) || physician.getFirst_name().startsWith(name))
                .collect(Collectors.toList());
    }
}

