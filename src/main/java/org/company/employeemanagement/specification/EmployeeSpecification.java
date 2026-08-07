package org.company.employeemanagement.specification;

import org.company.employeemanagement.entity.Employee;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {
    public static Specification<Employee> hasSalaryGreaterThan(double salary)
    {
        return (root, query, cb) -> cb.greaterThan(root.get("salary"), salary);
    }

    public static Specification<Employee> belongsToDepartment(String departmentName)
    {
        return (root, query, cb) -> cb.like(cb.lower(root.get("department").get("name")),departmentName.toLowerCase() + "%");
    }

    public static Specification<Employee> hasNameContaining(String name)
    {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")),name.toLowerCase()+"%");
    }
}
