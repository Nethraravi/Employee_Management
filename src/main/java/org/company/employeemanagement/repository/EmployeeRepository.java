package org.company.employeemanagement.repository;

import jakarta.persistence.LockModeType;
import org.company.employeemanagement.dto.EmployeeResponseDTO;
import org.company.employeemanagement.dto.EmployeeSummaryDTO;
import org.company.employeemanagement.dto.EmployeeView;
import org.company.employeemanagement.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    @Query(""" 
            SELECT e FROM Employee e WHERE e.salary > :salary 
            """ )
    List<Employee> findEmployeesWithSalaryGreaterThan(@Param("salary") double salary);

    //Page<Employee> findAll(Pageable pageable); ---> Spring injects this at runtime from SimpleJpaRepository which implements it internally

    @Query("""
            SELECT new org.company.employeemanagement.dto.EmployeeSummaryDTO(e.name,e.salary) FROM Employee e 
    """)
    List<EmployeeSummaryDTO> getEmployeeSummaries();

    @Query("""
SELECT e FROM Employee e
""")
    List<EmployeeView> getEmployeeViews();

    @Query("SELECT MAX(e.salary) FROM Employee e")
    Double getHighestSalary();

    @Query("SELECT AVG(e.salary) FROM Employee e")
    Double getAverageSalary();

    long count();


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT e FROM Employee e WHERE e.id = :id")
    Employee findEmployeeForUpdate(Long id);

}
