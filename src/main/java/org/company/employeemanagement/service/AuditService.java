package org.company.employeemanagement.service;

import org.company.employeemanagement.entity.Employee;
import org.company.employeemanagement.exception.EmployeeNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.company.employeemanagement.entity.AuditLog;
import org.company.employeemanagement.repository.AuditRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import java.time.LocalDateTime;

@Service
public class AuditService {
    private final AuditRepository auditRepository;
    private static final Logger logg = LoggerFactory.getLogger(AuditService.class);


    public AuditService(AuditRepository auditRepository)
    {
        this.auditRepository=auditRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAudit(String message)
    {
        AuditLog log = new AuditLog();
        log.setMessage(message);
        auditRepository.save(log);
        logg.info("Audit entry created for employee creation.");
        //System.out.println("Audit Saved");
    }



}
