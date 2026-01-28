package com.residenttrackerreworked.backend;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent, Long> {

    Optional<Agent> findByEmail(String email);
}
