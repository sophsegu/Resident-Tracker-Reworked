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

    // ---------- LOGIN ----------
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

        agentRepository.save(agent);

        return ResponseEntity.ok("Test user created");
    }
}
