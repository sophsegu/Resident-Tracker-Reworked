package com.residenttrackerreworked.backend;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final AgentRepository agentRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            AuthService authService,
            AgentRepository agentRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.authService = authService;
        this.agentRepository = agentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        System.out.println("Login attempt for email: " + request.getEmail());
        System.out.println("Password provided: " + request.getPassword());
        // Call your existing AuthService.login method
        LoginResponse response = authService.login(request);

        System.out.println("Login response status: " + response.getStatus());
        // If login failed
        if ("error".equals(response.getStatus())) {
            return ResponseEntity.status(401).body(response);
        }

        // If login succeeded, return JSON with user info
        return ResponseEntity.ok(response);
    }

    // ---------- DEV ONLY ----------
    @PostMapping("/dev/create-test-user")
    public ResponseEntity<?> createTestUser() {

        if (agentRepository.findByEmail("soph.segu@gmail.com").isPresent()) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        Agent agent = new Agent();
        agent.setFirstName("Sophie");
        agent.setLastName("Seguin");
        agent.setEmail("soph.segu@gmail.com");

        agent.setHash(passwordEncoder.encode("1234"));
        System.out.println("Encoded password: " + agent.getHash());

        agentRepository.save(agent);

        return ResponseEntity.ok("Test user created");
    }
}
