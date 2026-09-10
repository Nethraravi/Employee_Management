package org.company.employeemanagement.dto;

public class EmployeeResponseDTO {
    private Long id;
    private String name;
    private Double salary;
    private String departmentName;
    private Long departmentId;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id=id;
    }

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

    public String getDepartmentName()
    {
        return departmentName;
    }

    public void setDepartmentName(String departmentName)
    {
        this.departmentName=departmentName;
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
