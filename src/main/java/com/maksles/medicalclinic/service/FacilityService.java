package com.maksles.medicalclinic.service;

import com.maksles.medicalclinic.dto.CreateFacilityCommand;
import com.maksles.medicalclinic.exception.FacilityAlreadyExistsException;
import com.maksles.medicalclinic.model.Facility;
import com.maksles.medicalclinic.repository.FacilityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;

   @Transactional(readOnly = true)
   public Page<Facility> getFacilities(Pageable pageable) {
       return facilityRepository.findAll(pageable);
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
