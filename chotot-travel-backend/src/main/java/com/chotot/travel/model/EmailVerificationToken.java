package com.chotot.travel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class EmailVerificationToken {

    @Id
    private String token;

    private String email;

    private Date expiryDate;

    public EmailVerificationToken() {}

    public EmailVerificationToken(String token, String email, Date expiryDate) {
        this.token = token;
        this.email = email;
        this.expiryDate = expiryDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
