package com.maksles.medicalclinic.controller;

import com.maksles.medicalclinic.dto.CreateFacilityCommand;
import com.maksles.medicalclinic.dto.FacilityDto;
import com.maksles.medicalclinic.mapper.FacilityMapper;
import com.maksles.medicalclinic.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facilities")
@RequiredArgsConstructor
public class FacilityController {

    private final FacilityService facilityService;
    private final FacilityMapper facilityMapper;

    @GetMapping
    public List<FacilityDto> getFacilities() {
        return facilityService.getFacilities().stream()
                .map(facilityMapper::toDto)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FacilityDto addFacility(@RequestBody CreateFacilityCommand command) {
        return facilityMapper.toDto(facilityService.addFacility(command));
    }
}
