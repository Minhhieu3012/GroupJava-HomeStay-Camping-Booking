package com.chotot.travel.repository;

import com.chotot.travel.model.EmailVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, String> {
    EmailVerificationToken findByToken(String token);
}
