package com.residenttrackerreworked.backend;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AgentRepository agentRepository;

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

        String computedHash =
                PasswordUtil.hash(
                        request.getPassword()
                );

        if (!computedHash.equals(agent.getHash())) {
            response.setStatus("error");
            response.setMessage("Invalid email or password");
            return response;
        }

        response.setStatus("success");
        response.setAgentId(agent.getIdentifier());
        response.setFirstName(agent.getFirstName());
        response.setLastName(agent.getLastName());

        return response;
    }

    public boolean authenticate(String email, String rawPassword) {
        Agent agent = agentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String computedHash = PasswordUtil.hash(rawPassword);

        return computedHash.equals(agent.getHash());
    }
}
