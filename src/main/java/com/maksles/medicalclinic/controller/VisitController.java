package com.maksles.medicalclinic.controller;

import com.maksles.medicalclinic.dto.CreateVisitCommand;
import com.maksles.medicalclinic.dto.PageDto;
import com.maksles.medicalclinic.dto.VisitDto;
import com.maksles.medicalclinic.mapper.VisitMapper;
import com.maksles.medicalclinic.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public PageDto<VisitDto> getVisits(@PageableDefault(size = 20, sort = "startTime")Pageable pageable) {
        return PageDto.from(visitService.getVisits(pageable).map(visitMapper::toDto));
    }

    // GET /visits/patients/5  - wszystkie umówione wizyty pacjenta
    @GetMapping("/patients/{patientId}")
    public PageDto<VisitDto> getPatientVisists(@PathVariable Long patientId, @PageableDefault(size = 20, sort = "startTime") Pageable pageable) {
        return PageDto.from(visitService.getPatientVisits(patientId, pageable).map(visitMapper::toDto));
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