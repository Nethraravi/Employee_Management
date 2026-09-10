package org.company.employeemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.company.employeemanagement.entity.LeaveType;

import java.time.LocalDate;

public record LeaveRequestDTO(

        @NotNull
        LeaveType leaveType,

        @NotNull
        LocalDate startDate,

        @NotNull
        LocalDate endDate,

        @NotBlank
        String reason
) {
}