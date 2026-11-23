package com.maksles.medicalclinic.controller;

import com.maksles.medicalclinic.dto.CreateVisitCommand;
import com.maksles.medicalclinic.dto.VisitDto;
import com.maksles.medicalclinic.mapper.VisitMapper;
import com.maksles.medicalclinic.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;
    private final VisitMapper visitMapper;

    // GET /visits
    @GetMapping
    public List<VisitDto> getVisits() {
        return visitService.getVisits().stream()
                .map(visitMapper::toDto)
                .toList();
    }

    // GET /visits/patients/5  - wszystkie umówione wizyty pacjenta
    @GetMapping("/patients/{patientId}")
    public List<VisitDto> getPatientVisits(@PathVariable Long patientId) {
        return visitService.getPatientVisits(patientId).stream()
                .map(visitMapper::toDto)
                .toList();
    }

    // POST /visits  (body: doctorId, startTime, endTime)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VisitDto addVisit(@RequestBody CreateVisitCommand command) {
        return visitMapper.toDto(visitService.addVisit(command));
    }

    // PATCH /visits/1/patients/5  - pacjent zapisuje się na wizytę
    @PatchMapping("/{visitId}/patients/{patientId}")
    public VisitDto assignPatient(@PathVariable Long visitId, @PathVariable Long patientId) {
        return visitMapper.toDto(visitService.assignPatient(visitId, patientId));
    }
}