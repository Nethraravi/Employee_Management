package org.company.employeemanagement.dto;

public class AuthenticationResponse {
    private String token;
    private boolean mustChangePassword;

    public AuthenticationResponse()
    {

    }

    public AuthenticationResponse(String token, boolean mustChangePassword)
    {
        this.token=token;
        this.mustChangePassword = mustChangePassword;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token=token;
    }

    public boolean isMustChangePassword() {
        return mustChangePassword;
    }

    public void setMustChangePassword(boolean mustChangePassword) {
        this.mustChangePassword = mustChangePassword;
    }
}
