package org.company.employeemanagement.mapper;


import org.company.employeemanagement.dto.EmployeeResponseDTO;
import org.company.employeemanagement.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mapping(source = "department.name", target = "departmentName")
    @Mapping(source = "department.id", target = "departmentId")
    EmployeeResponseDTO toDto(Employee employee);
}
