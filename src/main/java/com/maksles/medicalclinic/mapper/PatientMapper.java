package com.maksles.medicalclinic.mapper;

import com.maksles.medicalclinic.dto.PatientDto;
import com.maksles.medicalclinic.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = UserMapper.class, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PatientMapper {

    PatientDto toDto(Patient patient);
}
