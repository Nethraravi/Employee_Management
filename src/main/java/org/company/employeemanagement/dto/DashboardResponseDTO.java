package org.company.employeemanagement.dto;

public class DashboardResponseDTO {
    private Long totalEmployees;
    private Long totalDepartments;
    private Double highestSalary;
    private Double averageSalary;

    public Long getTotalEmployees()
    {
        return totalEmployees;
    }

    public void setTotalEmployees(Long totalEmployees)
    {
        this.totalEmployees = totalEmployees;
    }

    public Long getTotalDepartments()
    {
        return totalDepartments;
    }

    public void setTotalDepartments(Long totalDepartments)
    {
        this.totalDepartments=totalDepartments;
    }

    public Double getHighestSalary()
    {
        return highestSalary;
    }

    public void setHighestSalary(Double highestSalary)
    {
        this.highestSalary=highestSalary;
    }

    public Double getAverageSalary()
    {
        return averageSalary;
    }

    public void setAverageSalary(Double averageSalary)
    {
        this.averageSalary=averageSalary;
    }
}
