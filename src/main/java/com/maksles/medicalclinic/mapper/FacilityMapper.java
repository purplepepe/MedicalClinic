package com.maksles.medicalclinic.mapper;

import com.maksles.medicalclinic.dto.FacilityDto;
import com.maksles.medicalclinic.model.Facility;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface FacilityMapper {

    FacilityDto toDto(Facility facility);
}
