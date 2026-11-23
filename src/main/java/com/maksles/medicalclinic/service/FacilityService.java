package com.maksles.medicalclinic.service;

import com.maksles.medicalclinic.dto.CreateFacilityCommand;
import com.maksles.medicalclinic.exception.FacilityAlreadyExistsException;
import com.maksles.medicalclinic.model.Facility;
import com.maksles.medicalclinic.repository.FacilityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;

    public List<Facility> getFacilities() {
        return facilityRepository.findAll();
    }

    @Transactional
    public Facility addFacility(CreateFacilityCommand command) {
        Facility facility = Facility.create(command);
        validateNameNotTaken(command.name());
        return facilityRepository.save(facility);
    }

    private void validateNameNotTaken(String name) {
        if (facilityRepository.existsByNameIgnoreCase(name)) {
            throw new FacilityAlreadyExistsException(name);
        }
    }
}
