package org.company.employeemanagement.controller;

import jakarta.validation.Valid;
import org.company.employeemanagement.common.ApiResponse;
import org.company.employeemanagement.dto.AuthenticationResponse;
import org.company.employeemanagement.dto.ChangePasswordRequestDTO;
import org.company.employeemanagement.dto.LoginRequestDTO;
import org.company.employeemanagement.entity.AppUser;
import org.company.employeemanagement.repository.AppUserRepository;
import org.company.employeemanagement.service.JwtService;
import org.company.employeemanagement.service.PasswordService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AppUserRepository appUserRepository;
    private final PasswordService passwordService;


    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, AppUserRepository appUserRepository, PasswordService passwordService)
    {
        this.authenticationManager=authenticationManager;
        this.jwtService=jwtService;
        this.appUserRepository=appUserRepository;
        this.passwordService=passwordService;
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequestDTO request)
    {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtService.generateToken(userDetails);
        AppUser user = appUserRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new AuthenticationResponse(jwt, user.isMustChangePassword());
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<String>> changePassword(
            @Valid @RequestBody ChangePasswordRequestDTO request,
            Authentication authentication) {

        String username = authentication.getName();

        passwordService.changePassword(username, request.currentPassword(),request.newPassword());

        return ResponseEntity.ok(
                new ApiResponse<>(true,"Password changed successfully",null
                )
        );
    }
}