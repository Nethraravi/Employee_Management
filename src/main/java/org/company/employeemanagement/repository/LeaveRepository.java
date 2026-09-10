package org.company.employeemanagement.repository;

import org.company.employeemanagement.entity.Leave;
import org.company.employeemanagement.entity.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave, Long> {

    List<Leave> findByEmployeeId(Long employeeId);

    List<Leave> findByStatus(LeaveStatus status);
}