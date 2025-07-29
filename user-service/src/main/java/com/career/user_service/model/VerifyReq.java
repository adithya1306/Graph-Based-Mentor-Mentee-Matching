package com.career.user_service.model;

public class VerifyReq {
    private String email;
    private String otp;

    public VerifyReq() {
    }

    public VerifyReq(String email, String otp) {
        this.email = email;
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
