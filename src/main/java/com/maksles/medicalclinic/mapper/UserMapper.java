package com.maksles.medicalclinic.mapper;

import com.maksles.medicalclinic.dto.UserDto;
import com.maksles.medicalclinic.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

    UserDto toDto(User user);
}
