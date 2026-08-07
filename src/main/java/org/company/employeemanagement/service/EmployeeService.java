package org.company.employeemanagement.service;

import jakarta.transaction.Transactional;
import org.company.employeemanagement.dto.EmployeeRequestDTO;
import org.company.employeemanagement.dto.EmployeeResponseDTO;
import org.company.employeemanagement.entity.Department;
import org.company.employeemanagement.entity.Employee;
import org.company.employeemanagement.exception.EmployeeNotFoundException;
import org.company.employeemanagement.mapper.EmployeeMapper;
import org.company.employeemanagement.repository.DepartmentRepository;
import org.company.employeemanagement.repository.EmployeeRepository;
import org.company.employeemanagement.specification.EmployeeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class EmployeeService {
    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final AuditService auditService;
    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    public EmployeeService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, AuditService auditService, EmployeeMapper employeeMapper)
    {
        this.employeeRepository=employeeRepository;
        this.departmentRepository=departmentRepository;
        this.auditService=auditService;
        this.employeeMapper=employeeMapper;
    }

    //To visualize REQUIRED
    @Transactional
    public void testRequiredPropagation()
    {
        Employee e1 = new Employee();
        e1.setName("John_TX");
        e1.setSalary(50000);

        employeeRepository.save(e1);

        log.info("Employee saved successfully. Waiting for transaction commit...");
        //System.out.println("Before commit");

        //No exception

//        if(true) // forced exception
//        {
//            throw new RuntimeException("Simulated crash");
//        }
    }

    //to visualize REQUIRED_NEW
    @Transactional
    public void createEmployeeWithAudit()
    {
        Employee employee = new Employee();
        employee.setName("Propagation_Test");
        employee.setSalary(50000);

        log.info("Creating employee...");

        employeeRepository.save(employee);
        log.info("Employee saved. Creating audit log...");
        auditService.saveAudit("Employee creation started");
        log.error("Rolling back employee transaction for demonstration.");

        throw new RuntimeException("Employee creation failed");
    }

    public void createEmployee()
    {
        saveEmployeeTransactional();
    }

    @Transactional
    public void saveEmployeeTransactional()
    {
        log.info("Transaction started.");
        //System.out.println("Transaction Active...");
    }

    @Transactional
    public Employee loadEmployeeForUserA()
    {
        log.info("User A is loading employee.");
        return employeeRepository.findById(1L).get();
    }

    @Transactional
    public Employee loadEmployeeForUserB()
    {
        log.info("User B is loading employee.");
        return employeeRepository.findById(1L).get();
    }

    @Transactional
    public void testPessimisticLock()
    {
        Employee employee = employeeRepository.findEmployeeForUpdate(1L);
        log.info("Pessimistic lock acquired on employee 1.");
        //System.out.println("Lock Acquired");
        try
        {
            Thread.sleep(30000);
        }
        catch (InterruptedException e)
        {
            log.error("Thread interrupted while holding pessimistic lock.",e);
            Thread.currentThread().interrupt();
        }
        employee.setSalary(employee.getSalary()+1000);
        log.info("Employee salary updated.");
    }

    public EmployeeResponseDTO getEmployee(Long id)
    {
        log.info("Fetching employee with id {}",id);
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> { log.error("Employee {} not found", id); return new EmployeeNotFoundException("Employee not found ");});
        log.info("Employee {} fetched successfully",id);
        return employeeMapper.toDto(employee);
    }

    public Page<EmployeeResponseDTO> getAllEmployees(String search, String searchBy,  Pageable pageable)
    {
        Specification<Employee> specification = (root, query, cb) -> cb.conjunction();

        if(search!=null && !search.isBlank())
        {
            if("name".equals(searchBy))
            {
                specification = specification.and(EmployeeSpecification.hasNameContaining(search));
            }
            else if("department".equals(searchBy))
            {
                specification = specification.and(EmployeeSpecification.belongsToDepartment(search));
            }
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return employeeRepository.findAll(specification, pageable).map(employeeMapper::toDto);
    }

    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO request) {

        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setSalary(request.getSalary());

        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(() -> new RuntimeException("Department not found"));

        employee.setDepartment(department);

        Employee saved = employeeRepository.save(employee);

        return employeeMapper.toDto(saved);
    }

    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO request) {
        Employee employee = employeeRepository.findById(id).orElseThrow(()->new EmployeeNotFoundException("Employee not found"));
        employee.setName(request.getName());
        employee.setSalary(request.getSalary());
        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(()->new RuntimeException("Department not found"));
        employee.setDepartment(department);
        Employee updated = employeeRepository.save(employee);
        return employeeMapper.toDto(updated);
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        employeeRepository.delete(employee);
    }
}
