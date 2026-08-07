package org.company.employeemanagement.mapper;

import org.company.employeemanagement.dto.DepartmentRequestDTO;
import org.company.employeemanagement.dto.DepartmentResponseDTO;
import org.company.employeemanagement.entity.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    Department toEntity(DepartmentRequestDTO dto);
    DepartmentResponseDTO toDto(Department department);
}
