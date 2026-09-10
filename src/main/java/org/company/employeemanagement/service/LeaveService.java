package org.company.employeemanagement.service;

import lombok.RequiredArgsConstructor;
import org.company.employeemanagement.dto.LeaveRequestDTO;
import org.company.employeemanagement.dto.LeaveResponseDTO;
import org.company.employeemanagement.entity.AppUser;
import org.company.employeemanagement.entity.Leave;
import org.company.employeemanagement.entity.LeaveStatus;
import org.company.employeemanagement.mapper.LeaveMapper;
import org.company.employeemanagement.repository.AppUserRepository;
import org.company.employeemanagement.repository.LeaveRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveService {

    private final LeaveRepository leaveRepository;
    private final LeaveMapper leaveMapper;
    private final AppUserRepository appUserRepository;

    /*
     * EMPLOYEE
     * Apply for leave.
     */
    @Transactional
    public LeaveResponseDTO applyLeave(
            LeaveRequestDTO requestDTO,
            String username) {

        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (appUser.getEmployee() == null) {
            throw new RuntimeException("No employee linked to this user");
        }

        validateDates(requestDTO.startDate(), requestDTO.endDate());

        Leave leave = leaveMapper.toEntity(requestDTO);

        leave.setEmployeeId(appUser.getEmployee().getId());
        leave.setStatus(LeaveStatus.PENDING);

        Leave savedLeave = leaveRepository.save(leave);

        return leaveMapper.toResponseDTO(savedLeave);
    }

    /*
     * EMPLOYEE
     * View only the logged-in employee's leaves.
     */
    public List<LeaveResponseDTO> getMyLeaves(String username) {

        AppUser appUser = getUser(username);

        if (appUser.getEmployee() == null) {
            throw new RuntimeException("No employee linked to this user");
        }

        Long employeeId = appUser.getEmployee().getId();

        return leaveRepository.findByEmployeeId(employeeId)
                .stream()
                .map(leaveMapper::toResponseDTO)
                .toList();
    }

    /*
     * ADMIN
     * View all leave applications.
     */
    public List<LeaveResponseDTO> getAllLeaves() {

        return leaveRepository.findAll()
                .stream()
                .map(leaveMapper::toResponseDTO)
                .toList();
    }

    /*
     * ADMIN
     * View a particular leave.
     */
    public LeaveResponseDTO getLeaveById(Long id) {

        Leave leave = findLeave(id);

        return leaveMapper.toResponseDTO(leave);
    }

    /*
     * ADMIN
     * View leaves belonging to an employee.
     */
    public List<LeaveResponseDTO> getLeavesByEmployee(Long employeeId) {

        return leaveRepository.findByEmployeeId(employeeId)
                .stream()
                .map(leaveMapper::toResponseDTO)
                .toList();
    }

    /*
     * ADMIN
     * Filter leaves by status.
     */
    public List<LeaveResponseDTO> getLeavesByStatus(
            LeaveStatus status) {

        return leaveRepository.findByStatus(status)
                .stream()
                .map(leaveMapper::toResponseDTO)
                .toList();
    }

    /*
     * ADMIN
     * Approve or reject a leave.
     */
    @Transactional
    public LeaveResponseDTO updateLeaveStatus(
            Long id,
            LeaveStatus status) {

        Leave leave = findLeave(id);

        validateStatusChange(leave, status);

        leave.setStatus(status);

        Leave updatedLeave = leaveRepository.save(leave);

        return leaveMapper.toResponseDTO(updatedLeave);
    }

    /*
     * ADMIN
     * Delete a leave.
     */
    @Transactional
    public void deleteLeave(Long id) {

        Leave leave = findLeave(id);

        leaveRepository.delete(leave);
    }

    private AppUser getUser(String username) {

        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Leave findLeave(Long id) {

        return leaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));
    }

    private void validateDates(
            LocalDate startDate,
            LocalDate endDate) {

        if (endDate.isBefore(startDate)) {
            throw new RuntimeException(
                    "End date cannot be before start date");
        }

        if (startDate.isBefore(LocalDate.now())) {
            throw new RuntimeException(
                    "Leave cannot be applied for a past date");
        }
    }

    private void validateStatusChange(
            Leave leave,
            LeaveStatus newStatus) {

        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new RuntimeException(
                    "Only pending leaves can be approved or rejected");
        }

        if (newStatus != LeaveStatus.APPROVED
                && newStatus != LeaveStatus.REJECTED) {

            throw new RuntimeException(
                    "Leave can only be approved or rejected");
        }
    }
}