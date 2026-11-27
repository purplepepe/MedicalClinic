package com.maksles.medicalclinic.controller;

import com.maksles.medicalclinic.dto.*;
import com.maksles.medicalclinic.mapper.PatientMapper;
import com.maksles.medicalclinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;


@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final PatientMapper patientMapper;

    @GetMapping
    public PageDto<PatientDto> getPatients(@PageableDefault(size = 20, sort = "id")
    Pageable pageable) {
        return PageDto.from(patientService.getPatients(pageable).map(patientMapper::toDto));
    }

    @GetMapping("/{id}")
    public PatientDto getPatient(@PathVariable Long id) {
        return patientMapper.toDto(patientService.getPatient(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //201 zamiast domyślnego 200
    public PatientDto addPatient(@RequestBody CreatePatientCommand command) {
        return patientMapper.toDto(patientService.addPatient(command));
    }

    @PutMapping("/{id}")
    public PatientDto updatePatient(@PathVariable Long id, @RequestBody UpdatePatientCommand command) {
        return patientMapper.toDto(patientService.updatePatient(id, command));
    }

    @PatchMapping("/{id}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable Long id, @RequestBody ChangePasswordCommand command) {
        patientService.changePassword(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
    }
}
