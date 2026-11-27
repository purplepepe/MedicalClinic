package com.maksles.medicalclinic.controller;

import com.maksles.medicalclinic.dto.CreateDoctorCommand;
import com.maksles.medicalclinic.dto.DoctorDto;
import com.maksles.medicalclinic.dto.PageDto;
import com.maksles.medicalclinic.mapper.DoctorMapper;
import com.maksles.medicalclinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;

    @GetMapping
    public PageDto<DoctorDto> getDoctors(@PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return PageDto.from(doctorService.getDoctors(pageable).map(doctorMapper::toDto));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorDto addDoctor(@RequestBody CreateDoctorCommand command) {
        return doctorMapper.toDto(doctorService.addDoctor(command));
    }

    @PatchMapping("/{doctorId}/facilities/{facilityId}")
    public DoctorDto assignFacility(@PathVariable Long doctorId, @PathVariable Long facilityId) {
        return doctorMapper.toDto(doctorService.assignFacility(doctorId, facilityId));
    }
}
