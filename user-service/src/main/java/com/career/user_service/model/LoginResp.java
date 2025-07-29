package com.career.user_service.model;

public class LoginResp {
    private String firstName;
    private String token;
    private String role;

    public LoginResp() {
    }

    public LoginResp(String firstName, String token, String role) {
        this.firstName = firstName;
        this.token = token;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
