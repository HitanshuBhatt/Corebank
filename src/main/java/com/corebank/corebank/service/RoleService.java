package com.corebank.corebank.service;

import com.corebank.corebank.model.Role;
import com.corebank.corebank.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository repo;

    public RoleService(RoleRepository repo) {
        this.repo = repo;
    }

    public Optional<Role> findByName(String name) {
        return repo.findByName(name);
    }

    public Role save(Role role) {
        return repo.save(role);
    }
}
