package org.company.employeemanagement.repository;

import org.company.employeemanagement.entity.Department;
import org.company.employeemanagement.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long>, JpaSpecificationExecutor<Department> {
    @Query("""
    SELECT d FROM Department d JOIN FETCH d.employees
""")
    List<Department> findAllWithEmployees();

    List<Department> findByName(String name);

    boolean existsByNameIgnoreCase(String name);

    @EntityGraph(attributePaths = "employees")
    Page<Department> findAll(Pageable pageable);

}
