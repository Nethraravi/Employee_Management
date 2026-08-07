package org.company.employeemanagement;

import org.company.employeemanagement.service.AuditService;
import org.company.employeemanagement.service.EmployeeService;
import org.company.employeemanagement.service.JPALearningService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeManagementApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagementApplication.class, args);
    }

    private final JPALearningService jpaLearningService;
    private final EmployeeService employeeService;
    private final AuditService auditService;

    public EmployeeManagementApplication(JPALearningService jpaLearningService, EmployeeService employeeService, AuditService auditService)
    {
        this.jpaLearningService=jpaLearningService;
        this.employeeService=employeeService;
        this.auditService=auditService;
    }

    @Override
    public void run(String[] args)
    {
        //jpaLearningService.testDirtyChecking();
        //jpaLearningService.testJPQL();
        //jpaLearningService.testNPlusOne();
        //jpaLearningService.testJoinFetch();
        //jpaLearningService.testPagination();
        //jpaLearningService.testDTOProjection();
        //jpaLearningService.testInterfaceProjection();
        //jpaLearningService.testEntityGraph();
        //employeeService.testRequiredPropagation();
        //employeeService.createEmployeeWithAudit();
        //auditService.saveAudit("Test");
        //auditService.saveAudit("Test");
        //jpaLearningService.testSpecification();
        //jpaLearningService.testDynamicSpecification();
        //employeeService.testPessimisticLock();
    }
}
