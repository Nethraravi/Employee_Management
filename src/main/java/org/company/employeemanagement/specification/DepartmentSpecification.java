package org.company.employeemanagement.specification;

import org.company.employeemanagement.entity.Department;
import org.springframework.data.jpa.domain.Specification;

public class DepartmentSpecification {
    public static Specification<Department> hasNameContaining(String name)
    {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")),"%"+name.toLowerCase()+"%");
    }
}
