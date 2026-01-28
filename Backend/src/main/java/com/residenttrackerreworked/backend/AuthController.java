package com.residenttrackerreworked.backend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        boolean ok = authService.authenticate(
                request.getEmail(),
                request.getPassword()
        );

        if (!ok) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        return ResponseEntity.ok("Login successful");
    }
}
