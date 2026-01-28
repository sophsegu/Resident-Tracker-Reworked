package com.residenttrackerreworked.backend;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponse login(LoginRequest request) {
        LoginResponse response = new LoginResponse();

        Optional<User> userOpt =
                userRepository.findByEmailAndRole(request.getEmail(), request.getRole());

        if (userOpt.isEmpty()) {
            response.setStatus("error");
            response.setMessage("Invalid credentials");
            return response;
        }

        User user = userOpt.get();

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            response.setStatus("error");
            response.setMessage("Invalid credentials");
            return response;
        }

        response.setStatus("success");
        response.setUserId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        return response;
    }
}
 {
    
}
