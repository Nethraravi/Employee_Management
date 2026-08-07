package org.company.employeemanagement.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    private double salary;
    @Version
    private Long version;

    public Employee()
    {

    }

    public Long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name=name;
    }

    public double getSalary()
    {
        return salary;
    }

    public void setSalary(double salary)
    {
        this.salary=salary;
    }

    public Department getDepartment()
    {
        return department;
    }

    public void setDepartment(Department department)
    {
        this.department=department;
    }

    public Long getVersion()
    {
        return version;
    }

    @Override
    public String toString()
    {
        return "Employee{id="+id+", name="+name+", salary="+salary+"}";
    }

}
