package com.crud.confg;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JwtUtil {

    // Generate short random token (8 chars)
    public String generateToken(String email, String role) {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

    // Validate short token
    public boolean validateToken(String token) {
        return token != null && token.length() == 8;
    }
}
