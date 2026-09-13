package com.Admin_Register.Admin_Register.JwtUtil;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class AdminRegister_JwtUtil {
	
	private String secret = "PresidencyUniversityAdminLoginSecretKey2026@JaitejStrongSecretForJWT";
	
	private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
	
	public String generateToken(String username) {
		return Jwts.builder()
				.subject(username)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis()+1000 * 60 * 60 * 10))
				.signWith(getSigningKey())
				.compact();
			
	}
	
	public String extractUsername(String token) {
		return Jwts.parser()
				.verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
	}

}
