package com.maksles.medicalclinic.controller;

import com.maksles.medicalclinic.dto.CreateFacilityCommand;
import com.maksles.medicalclinic.dto.FacilityDto;
import com.maksles.medicalclinic.dto.PageDto;
import com.maksles.medicalclinic.mapper.FacilityMapper;
import com.maksles.medicalclinic.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/facilities")
@RequiredArgsConstructor
public class FacilityController {

    private final FacilityService facilityService;
    private final FacilityMapper facilityMapper;

    @GetMapping
    public PageDto<FacilityDto> getFacilities(@PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return PageDto.from(facilityService.getFacilities(pageable).map(facilityMapper::toDto));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FacilityDto addFacility(@RequestBody CreateFacilityCommand command) {
        return facilityMapper.toDto(facilityService.addFacility(command));
    }
}
