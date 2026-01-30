package com.residenttrackerreworked.backend;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AgentRepository agentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthService(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    public LoginResponse login(LoginRequest request) {

        LoginResponse response = new LoginResponse();

        Optional<Agent> agentOpt =
                agentRepository.findByEmail(request.getEmail());

        if (agentOpt.isEmpty()) {
            response.setStatus("error");
            response.setMessage("Invalid email or password");
            return response;
        }

        Agent agent = agentOpt.get();

        System.out.println(authenticate(request.getEmail(), request.getPassword()));

        if (!authenticate(request.getEmail(), request.getPassword())) {
            response.setStatus("error");
            response.setMessage("Invalid email or password");
            return response;
    }
    System.out.println("HERE");

        response.setStatus("success");
        response.setAgentId(agent.getIdentifier());
        response.setFirstName(agent.getFirstName());
        response.setLastName(agent.getLastName());

        return response;
    }

    public boolean authenticate(String email, String rawPassword) {
        Agent agent = agentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return passwordEncoder.matches(rawPassword, agent.getHash());
    }
}
