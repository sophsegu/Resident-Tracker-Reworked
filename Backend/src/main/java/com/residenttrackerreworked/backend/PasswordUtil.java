package com.residenttrackerreworked.backend;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordUtil {

    public static String hash(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String combined = salt + password;

            byte[] hashBytes = digest.digest(combined.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(hashBytes);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not available", e);
        }
    }

    public static String generateSalt() {
        byte[] salt = new byte[16];
        new java.security.SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

}
