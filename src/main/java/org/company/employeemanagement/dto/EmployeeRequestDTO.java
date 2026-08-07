package org.company.employeemanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Employee creation request")
public class EmployeeRequestDTO {

    @Schema(description = "Employee name", example = "John")
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @Schema(description = "Employee salary", example = "50000")
    @NotNull(message="Salary is required")
    @Positive(message = "Salary must be greater than 0")
    private Double salary;

    @Schema(description = "Department ID", example = "1")
    @NotNull(message = "Department is required")
    private Long departmentId;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name=name;
    }

    public Double getSalary()
    {
        return salary;
    }

    public void setSalary(Double salary)
    {
        this.salary=salary;
    }

    public Long getDepartmentId()
    {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId)
    {
        this.departmentId=departmentId;
    }
}
