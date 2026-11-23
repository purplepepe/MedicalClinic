package com.maksles.medicalclinic.mapper;

import com.maksles.medicalclinic.dto.VisitDto;
import com.maksles.medicalclinic.model.Visit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface VisitMapper {

    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "patient.id", target = "patientId")
    VisitDto toDto(Visit visit);
}
