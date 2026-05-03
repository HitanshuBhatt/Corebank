package com.corebank.corebank.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedHandler() {
        return (request, response, ex) -> {
            response.setStatus(401);
            response.getWriter().write("{\"error\": \"Unauthorized\"}");
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth

                // 🔓 PUBLIC PAGES
                .requestMatchers("/", "/login", "/register").permitAll()

                // ⭐ Loan apply page (GET + POST allowed)
                .requestMatchers("/loan/apply", "/loan/apply/**").authenticated()

                // ⭐ Loan approve/reject (only logged-in employee)
                .requestMatchers("/loan/approve/**", "/loan/reject/**").authenticated()

                // Static resources
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                // JWT AUTH API
                .requestMatchers("/api/auth/**").permitAll()

                // 🔐 PROTECTED PAGES
                .requestMatchers("/dashboard/**").authenticated()
                .requestMatchers("/accounts/**").authenticated()
                .requestMatchers("/transactions/**").authenticated()
                .requestMatchers("/deposit/**").authenticated()
                .requestMatchers("/withdraw/**").authenticated()
                .requestMatchers("/transfer/**").authenticated()

                // ✅ New: bill payments pages
                .requestMatchers("/bill-payments/**").authenticated()

                // API protected
                .requestMatchers("/api/**").authenticated()

                .anyRequest().permitAll()
        );

        // FORM LOGIN
        http.formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error=true")
                .permitAll()
        );

        // LOGOUT
        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
        );

        // SESSION FOR UI
        http.sessionManagement(sm ->
                sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        );

        // JWT FILTER FOR /api/**
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
