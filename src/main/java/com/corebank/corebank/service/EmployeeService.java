package com.corebank.corebank.service;

import com.corebank.corebank.model.Employee;
import com.corebank.corebank.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository repo;

    public EmployeeService(EmployeeRepository repo) {
        this.repo = repo;
    }

    public Employee save(Employee emp) {
        return repo.save(emp);
    }

    public Optional<Employee> findByUsername(String u) {
        return repo.findByUsername(u);
    }

    public Optional<Employee> findByEmployeeNumber(String e) {
        return repo.findByEmployeeNumber(e);
    }

    public List<Employee> getAll() {
        return repo.findAll();
    }

    public Optional<Employee> getById(Long id) {
        return repo.findById(id);
    }
}
