package org.company.employeemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "department") //Lazy loading by default.
    @JsonIgnore
    private List<Employee> employees = new ArrayList<>();

    public List<Employee> getEmployees()
    {
        return employees;
    }

    public Department()
    {

    }

    public void setName(String name) {
        this.name=name;
    }

    public Long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return "Department{id="+id+", name="+name+"}";
    }

    public void addEmployee(Employee emp)   //Helper class used for Java Object Synchronization with Employees.
    {
        System.out.println(employees.add(emp));
        emp.setDepartment(this);
    }

    public void removeEmployee(Employee emp) //Same Helper class as addEmployee.
    {
        System.out.println(employees.remove(emp));
        emp.setDepartment(null);
    }
}
