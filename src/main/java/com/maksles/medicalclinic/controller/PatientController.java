package com.maksles.medicalclinic.controller;

import com.maksles.medicalclinic.dto.ChangePasswordCommand;
import com.maksles.medicalclinic.dto.CreatePatientCommand;
import com.maksles.medicalclinic.dto.PatientDto;
import com.maksles.medicalclinic.dto.UpdatePatientCommand;
import com.maksles.medicalclinic.mapper.PatientMapper;
import com.maksles.medicalclinic.model.Patient;
import com.maksles.medicalclinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final PatientMapper patientMapper;

    @GetMapping
    public List<PatientDto> getPatients() {
        return patientService.getPatients().stream()
                .map(patientMapper::toDto)
                .toList();
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

    @PatchMapping("/{email}/password")
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
