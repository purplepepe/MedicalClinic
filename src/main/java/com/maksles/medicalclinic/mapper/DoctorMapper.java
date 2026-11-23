package com.maksles.medicalclinic.mapper;

import com.maksles.medicalclinic.dto.DoctorDto;
import com.maksles.medicalclinic.model.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = FacilityMapper.class, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface DoctorMapper {

    DoctorDto toDto(Doctor doctor);
}
