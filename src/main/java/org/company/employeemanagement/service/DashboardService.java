package org.company.employeemanagement.service;

import org.company.employeemanagement.dto.DashboardResponseDTO;
import org.company.employeemanagement.entity.Employee;
import org.company.employeemanagement.repository.DepartmentRepository;
import org.company.employeemanagement.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public DashboardService(EmployeeRepository employeeRepository,
                            DepartmentRepository departmentRepository)
    {
        this.employeeRepository=employeeRepository;
        this.departmentRepository=departmentRepository;
    }

    public DashboardResponseDTO getDashboard()
    {
        DashboardResponseDTO dto = new DashboardResponseDTO();
        dto.setTotalEmployees(employeeRepository.count());
        dto.setTotalDepartments(departmentRepository.count());
        dto.setHighestSalary(employeeRepository.getHighestSalary());
        dto.setAverageSalary(employeeRepository.getAverageSalary());
        return dto;
    }
}
