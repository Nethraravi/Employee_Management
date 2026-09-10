package org.company.employeemanagement.service;

import jakarta.transaction.Transactional;
import org.company.employeemanagement.dto.ChangePasswordRequestDTO;
import org.company.employeemanagement.entity.AppUser;
import org.company.employeemanagement.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class PasswordService {

    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;

    private final SecureRandom secureRandom = new SecureRandom();

    public PasswordService(PasswordEncoder passwordEncoder, AppUserRepository appUserRepository) {
        this.passwordEncoder = passwordEncoder;
        this.appUserRepository=appUserRepository;
    }

    public String generateTemporaryPassword() {

        String characters =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                        "abcdefghijklmnopqrstuvwxyz" +
                        "0123456789" +
                        "@#$%";

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 12; i++) {
            password.append(
                    characters.charAt(
                            secureRandom.nextInt(characters.length())
                    )
            );
        }

        return password.toString();
    }

    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Transactional
    public void changePassword(String username, String currentPassword, String newPassword) {

        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!matches(currentPassword,user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        user.setPassword(encode(newPassword));
        user.setMustChangePassword(false);
        appUserRepository.save(user);
    }
}