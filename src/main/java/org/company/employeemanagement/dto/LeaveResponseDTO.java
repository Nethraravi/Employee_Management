package org.company.employeemanagement.dto;

import org.company.employeemanagement.entity.LeaveStatus;
import org.company.employeemanagement.entity.LeaveType;

import java.time.LocalDate;

public record LeaveResponseDTO(

        Long id,
        Long employeeId,
        LeaveType leaveType,
        LocalDate startDate,
        LocalDate endDate,
        String reason,
        LeaveStatus status
) {
}