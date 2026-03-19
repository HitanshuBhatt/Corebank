package com.corebank.corebank.service;

import com.corebank.corebank.model.Employee;
import com.corebank.corebank.model.Role;
import com.corebank.corebank.model.User;
import com.corebank.corebank.repository.EmployeeRepository;
import com.corebank.corebank.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    public CustomUserDetailsService(UserRepository userRepository,
                                    EmployeeRepository employeeRepository) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1) Try customer user
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            List<GrantedAuthority> authorities = user.getRoles()
                    .stream()
                    .map(Role::getName)
                    .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r.toUpperCase())
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    user.isEnabled(),
                    true,
                    true,
                    true,
                    authorities
            );
        }

        // 2) Try employee
        Employee employee = employeeRepository.findByUsername(username).orElse(null);
        if (employee != null) {
            String roleName = employee.getRole() != null ? employee.getRole() : "EMPLOYEE";
            GrantedAuthority authority =
                    new SimpleGrantedAuthority("ROLE_" + roleName.toUpperCase());

            return new org.springframework.security.core.userdetails.User(
                    employee.getUsername(),
                    employee.getPassword(),
                    employee.isEnabled(),
                    true,
                    true,
                    true,
                    List.of(authority)
            );
        }

        throw new UsernameNotFoundException("User or Employee not found: " + username);
    }
}
