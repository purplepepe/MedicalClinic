package com.maksles.medicalclinic.controller;

import com.maksles.medicalclinic.dto.ChangePasswordRequest;
import com.maksles.medicalclinic.dto.CreatePatientRequest;
import com.maksles.medicalclinic.dto.PatientResponse;
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
    public List<PatientResponse> getPatients() {
        return patientService.getPatients().stream()
                .map(PatientResponse::from)
                .toList();
    }

    @GetMapping("/{email}")
    public PatientResponse getPatient(@PathVariable String email) {
        return PatientResponse.from(patientService.getPatient(email));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //201 zamiast domyślnego 200
    public PatientResponse addPatient(@RequestBody CreatePatientRequest request) {
        return PatientResponse.from(patientService.addPatient(request));
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable String email) {
        patientService.deletePatient(email);
    }

    @PutMapping("/{email}")
    public PatientResponse updatePatient(@PathVariable String email, @RequestBody Patient patient) {
        return PatientResponse.from(patientService.updatePatient(email, patient));
    }

    @PatchMapping("/{email}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable String email, @RequestBody ChangePasswordRequest request) {
        patientService.changePassword(email, request.oldPassword(), request.newPassword());
    }
}
