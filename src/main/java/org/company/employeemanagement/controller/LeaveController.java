package org.company.employeemanagement.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.company.employeemanagement.common.ApiResponse;
import org.company.employeemanagement.dto.LeaveRequestDTO;
import org.company.employeemanagement.dto.LeaveResponseDTO;
import org.company.employeemanagement.entity.LeaveStatus;
import org.company.employeemanagement.service.LeaveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    @PostMapping
    public ResponseEntity<ApiResponse<LeaveResponseDTO>> applyLeave(
            @Valid @RequestBody LeaveRequestDTO requestDTO,
            Authentication authentication) {

        LeaveResponseDTO response =
                leaveService.applyLeave(
                        requestDTO,
                        authentication.getName());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "Leave applied successfully",
                        response));
    }

    /*
     * ADMIN
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<LeaveResponseDTO>>> getAllLeaves() {

        List<LeaveResponseDTO> leaves =
                leaveService.getAllLeaves();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Leaves fetched successfully",
                        leaves));
    }

    /*
     * EMPLOYEE
     */
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<LeaveResponseDTO>>> getMyLeaves(
            Authentication authentication) {

        List<LeaveResponseDTO> leaves =
                leaveService.getMyLeaves(
                        authentication.getName());

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Your leaves fetched successfully",
                        leaves));
    }

    /*
     * ADMIN
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LeaveResponseDTO>> getLeaveById(
            @PathVariable Long id) {

        LeaveResponseDTO leave =
                leaveService.getLeaveById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Leave fetched successfully",
                        leave));
    }

    /*
     * ADMIN
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<List<LeaveResponseDTO>>> getLeavesByEmployee(
            @PathVariable Long employeeId) {

        List<LeaveResponseDTO> leaves =
                leaveService.getLeavesByEmployee(employeeId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Employee leaves fetched successfully",
                        leaves));
    }

    /*
     * ADMIN
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<LeaveResponseDTO>>> getLeavesByStatus(
            @PathVariable LeaveStatus status) {

        List<LeaveResponseDTO> leaves =
                leaveService.getLeavesByStatus(status);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Leaves fetched successfully",
                        leaves));
    }

    /*
     * ADMIN
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<LeaveResponseDTO>> updateLeaveStatus(
            @PathVariable Long id,
            @RequestParam LeaveStatus status) {

        LeaveResponseDTO leave =
                leaveService.updateLeaveStatus(id, status);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Leave status updated successfully",
                        leave));
    }

    /*
     * ADMIN
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteLeave(
            @PathVariable Long id) {

        leaveService.deleteLeave(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Leave deleted successfully",
                        "Leave ID: " + id));
    }
}