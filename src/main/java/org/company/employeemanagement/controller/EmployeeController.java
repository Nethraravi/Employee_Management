package org.company.employeemanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.company.employeemanagement.common.ApiResponse;
import org.company.employeemanagement.dto.EmployeeRequestDTO;
import org.company.employeemanagement.dto.EmployeeResponseDTO;
import org.company.employeemanagement.service.EmployeeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Tag(name = "Employee Management", description = "Operations related to employees")
@RestController
@RequestMapping("/employees")
public class EmployeeController
{
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService)
    {
        this.employeeService=employeeService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Fetch Employee by ID", description = "Returns an employee using the employee ID.")
    public ApiResponse<EmployeeResponseDTO> getEmployee(@PathVariable Long id)
    {
        EmployeeResponseDTO employee = employeeService.getEmployee(id);
        return new ApiResponse<>(true, "Employee fetched successfully", employee);
    }

    @Operation(summary = "Create Employee", description = "Creates a new employee after validating the request.")
    @PostMapping
    public ApiResponse<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO request)
    {
        EmployeeResponseDTO response = employeeService.createEmployee(request);
        return new ApiResponse<>(true, "Employee created successfully", response);
    }

    @PutMapping("/{id}")
    public ApiResponse<EmployeeResponseDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequestDTO request)
    {
        EmployeeResponseDTO response = employeeService.updateEmployee(id, request);
        return new ApiResponse<>(true, "Employee updated successfully", response);
    }

    @GetMapping
    @Operation(summary = "Fetch All Employees", description = "Returns all employees.")
    public ApiResponse<Page<EmployeeResponseDTO>> getAllEmployees(@RequestParam(required = false) String search, @RequestParam(required = false) String searchBy, Pageable pageable)
    {
        Page<EmployeeResponseDTO> employees = employeeService.getAllEmployees(search, searchBy, pageable);
        return new ApiResponse<>(true, "Employees fetched successfully", employees);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteEmployee(@PathVariable Long id)
    {
        employeeService.deleteEmployee(id);
        return new ApiResponse<>(true, "Employee deleted successfully", null);
    }
}