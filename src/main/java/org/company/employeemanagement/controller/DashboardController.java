package org.company.employeemanagement.controller;

import org.company.employeemanagement.common.ApiResponse;
import org.company.employeemanagement.dto.DashboardResponseDTO;
import org.company.employeemanagement.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService)
    {
        this.dashboardService=dashboardService;
    }

    @GetMapping("/dashboard")
    public ApiResponse<DashboardResponseDTO> getDashboard()
    {
        DashboardResponseDTO response = dashboardService.getDashboard();
        return new ApiResponse<>(true, "Dashboard fetched successfully", response);
    }
}
