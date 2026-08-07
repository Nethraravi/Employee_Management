package org.company.employeemanagement.controller;

import jakarta.validation.Valid;
import org.company.employeemanagement.common.ApiResponse;
import org.company.employeemanagement.dto.DepartmentRequestDTO;
import org.company.employeemanagement.dto.DepartmentResponseDTO;
import org.company.employeemanagement.entity.Department;
import org.company.employeemanagement.service.DepartmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService)
    {
        this.departmentService=departmentService;
    }

    @PostMapping
    public ApiResponse<DepartmentResponseDTO> create(@Valid @RequestBody DepartmentRequestDTO dto)
    {
        return new ApiResponse<>(true, "Department created", departmentService.createDepartment(dto));
    }

    @GetMapping
    public ApiResponse<Page<DepartmentResponseDTO>> getAll(@RequestParam(required = false) String search, Pageable pageable)
    {
        return new ApiResponse<>(true, "Departments fetched", departmentService.getAllDepartments(search, pageable));
    }

    @GetMapping("/{id}")
    public ApiResponse<DepartmentResponseDTO> getById(@PathVariable Long id)
    {
        return new ApiResponse<>(true, "Department fetched", departmentService.getDepartmentById(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<Department> update(@PathVariable Long id, @RequestBody DepartmentRequestDTO dto)
    {
        return new ApiResponse<>(true, "Department updated", departmentService.updateDepartment(id, dto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id)
    {
        departmentService.deleteDepartment(id);
        return new ApiResponse<>(true, "Department deleted", null);
    }
}
