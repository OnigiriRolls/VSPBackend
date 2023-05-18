package com.challenge.VSPBackend.controllers;

import com.challenge.VSPBackend.dtos.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.ResponseEntity;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final InMemoryUserDetailsManager userDetailsService;

    @PostMapping("/api/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        if (!userDetailsService.userExists(loginRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }

        String token = loginRequest.getUsername() + ":" + loginRequest.getPassword();
        return ResponseEntity.ok(Base64.encodeBase64String(token.getBytes()));
    }

    @PostMapping("/api/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logout successful");
    }
}
