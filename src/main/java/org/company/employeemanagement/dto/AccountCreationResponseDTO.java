package org.company.employeemanagement.dto;

public record AccountCreationResponseDTO(
        Long employeeId,
        String username,
        String temporaryPassword
) {
}