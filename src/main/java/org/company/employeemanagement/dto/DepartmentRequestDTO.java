package org.company.employeemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DepartmentRequestDTO {
    @NotBlank(message = "Department name is required")
    @Size(max=100, message="Department name cannot exceed 100 characters")
    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name=name;
    }
}
