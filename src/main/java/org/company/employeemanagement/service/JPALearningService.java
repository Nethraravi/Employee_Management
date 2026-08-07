package org.company.employeemanagement.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.company.employeemanagement.entity.Department;
import org.company.employeemanagement.entity.Employee;
import org.company.employeemanagement.repository.DepartmentRepository;
import org.company.employeemanagement.repository.EmployeeRepository;
import org.company.employeemanagement.specification.EmployeeSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JPALearningService {

    private DepartmentRepository departmentRepository;
    private EmployeeRepository employeeRepository;

    public JPALearningService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository)
    {
        this.departmentRepository=departmentRepository;
        this.employeeRepository=employeeRepository;
    }

    public void testLazyLoading()
    {
        Department dept = departmentRepository.findById(1L).get();
        System.out.println("Department Loaded");
        System.out.println(dept.getName());
        System.out.println("Accessing employees now...");
        System.out.println(dept.getEmployees().size());
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void testDirtyChecking()
    {
        System.out.println("Entity Manager state(isOpen):"+entityManager.isOpen());

       Employee employee = employeeRepository.findById(6L).get();
       Employee employee1 = employeeRepository.findById(6L).get();
       System.out.println(employee==employee1);
       System.out.println("Old Salary : "+employee.getSalary());
        employee.setSalary(80000);
        employee.setSalary(90000);
        employee.setSalary(120000); // it doesn't cares about how many update occured checks it with first(ie, original value) and the last updated value if there is difference it returns insert into ... and update ... by only checking with first and last
        //employee.setSalary(50000); --> if the final value is same as the original value then no modification(no updation happens though there happened some)
        System.out.println("New Salary : "+employee.getSalary()); //no save () written after updation still returns updated value (Dirty checking - Persistence Context)
        System.out.println("Salary modified");
        System.out.println("Employee Saved");
    }

    public void testJPQL()
    {
        employeeRepository.findEmployeesWithSalaryGreaterThan(80000).forEach(System.out::println);
    }

    @Transactional
    public void testNPlusOne()
    {
        List<Department> departments = departmentRepository.findAll();
        for(Department department : departments)
        {
            System.out.println(department.getName());
            System.out.println(department.getEmployees().size());
        }
    }

    @Transactional
    public void testJoinFetch()
    {
        List<Department> departments = departmentRepository.findAllWithEmployees();
        for(Department department : departments)
        {
            System.out.println(department.getName());
            System.out.println(department.getEmployees().size());
        }
    }

    public void testPagination()
    {
        Pageable pageable = PageRequest.of(0, 3, Sort.by("salary").descending().and(Sort.by("name").ascending()));
        //Pageable pageable = PageRequest.of(0,3, Sort.by("salary").descending());
        Page<Employee> page = employeeRepository.findAll(pageable);
        page.getContent().forEach(System.out::println);
        System.out.println("Total Employees = "+page.getTotalElements());
        System.out.println("Total Pages = "+page.getTotalPages());
    }

    public void testDTOProjection()
    {
        employeeRepository.getEmployeeSummaries().forEach(System.out::println);
    }

    public void testInterfaceProjection()
    {
        employeeRepository.getEmployeeViews().forEach(v -> System.out.println(v.getName()+" "+v.getSalary()));
    }

    public void testEntityGraph()
    {
        List<Department> departments = departmentRepository.findAll();
        for(Department d : departments)
        {
            System.out.println(d.getName());
            System.out.println(d.getEmployees().size());
        }
    }

    @Transactional
    public void testOptimisticLocking()
    {
        Employee userA = employeeRepository.findById(1L).get();
        Employee userB = employeeRepository.findById(1L).get();
        System.out.println(userA.getVersion());
        System.out.println(userB.getVersion());
    }

    public void testSpecification()
    {
        Specification<Employee> spec = EmployeeSpecification.hasSalaryGreaterThan(80000).and(EmployeeSpecification.belongsToDepartment("IT"));
        employeeRepository.findAll(spec).forEach(System.out::println);
    }

    public void testDynamicSpecification()
    {
        String department = "IT";
        Double minSalary = 80000.0;
        String name = null;

        Specification<Employee> spec = (root, query, cb) -> cb.conjunction();

        if(department!=null)
        {
            spec=spec.and(EmployeeSpecification.belongsToDepartment(department));
        }

        if(minSalary!=null)
        {
            spec = spec.and(EmployeeSpecification.hasSalaryGreaterThan(minSalary));
        }

        if(name!=null)
        {
            spec=spec.and(EmployeeSpecification.hasNameContaining(name));
        }

        employeeRepository.findAll(spec).forEach(System.out::println);
    }
}
