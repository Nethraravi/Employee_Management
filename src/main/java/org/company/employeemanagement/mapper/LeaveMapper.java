package org.company.employeemanagement.mapper;

import org.company.employeemanagement.dto.LeaveRequestDTO;
import org.company.employeemanagement.dto.LeaveResponseDTO;
import org.company.employeemanagement.entity.Leave;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LeaveMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    Leave toEntity(LeaveRequestDTO requestDTO);

    LeaveResponseDTO toResponseDTO(Leave leave);
}