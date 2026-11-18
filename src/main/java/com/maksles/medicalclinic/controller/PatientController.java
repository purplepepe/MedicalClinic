package com.maksles.medicalclinic.controller;

import com.maksles.medicalclinic.dto.ChangePasswordCommand;
import com.maksles.medicalclinic.dto.CreatePatientCommand;
import com.maksles.medicalclinic.dto.PatientDto;
import com.maksles.medicalclinic.dto.UpdatePatientCommand;
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

    @GetMapping
    public List<PatientDto> getPatients() {
        return patientService.getPatients().stream()
                .map(PatientDto::from)
                .toList();
    }

    @GetMapping("/{email}")
    public PatientDto getPatient(@PathVariable String email) {
        return PatientDto.from(patientService.getPatient(email));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //201 zamiast domyślnego 200
    public PatientDto addPatient(@RequestBody CreatePatientCommand command) {
        return PatientDto.from(patientService.addPatient(command));
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable String email) {
        patientService.deletePatient(email);
    }

    @PutMapping("/{email}")
    public PatientDto updatePatient(@PathVariable String email, @RequestBody UpdatePatientCommand command) {
        return PatientDto.from(patientService.updatePatient(email, command));
    }

    @PatchMapping("/{email}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable String email, @RequestBody ChangePasswordCommand command) {
        patientService.changePassword(email, command);
    }
}
