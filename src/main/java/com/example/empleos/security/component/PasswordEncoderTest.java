package com.example.empleos.security.component;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "password123";  // Contraseña en texto plano
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println(encodedPassword);  // Esto te da la contraseña cifrada que puedes usar en import.sql
    }
}
