package org.company.employeemanagement.dto;

import org.company.employeemanagement.service.EmployeeService;

public class EmployeeSummaryDTO {

    private String name;
    private double salary;

    public EmployeeSummaryDTO(String name, double salary)
    {
        this.name=name;
        this.salary=salary;
    }

    @Override
    public String toString()
    {
        return "EmployeeSummaryDTO{name='"+name+"', salary="+salary+"}";
    }
}
