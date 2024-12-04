package com.example.AI_gen_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // GET pets/households - anyone (no auth needed)
                        .requestMatchers(HttpMethod.GET, "/api/pets/**", "/api/households/**").permitAll()

                        // Create operations - ADMIN only
                        .requestMatchers(HttpMethod.POST, "/api/pets/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/households/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/users/**").hasRole("ADMIN")

                        // Delete operations - ADMIN only
                        .requestMatchers(HttpMethod.DELETE, "/api/pets/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/households/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")

                        // Edit users - ADMIN only
                        .requestMatchers(HttpMethod.PUT, "/api/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/users/**").hasRole("ADMIN")

                        // Edit pets and households - ADMIN or USER
                        .requestMatchers(HttpMethod.PUT, "/api/pets/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PATCH, "/api/pets/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/api/households/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PATCH, "/api/households/**").hasAnyRole("ADMIN", "USER")

                        // Statistics - ADMIN or USER
                        .requestMatchers("/api/statistics/**").hasAnyRole("ADMIN", "USER")

                        // Any other requests require authentication
                        .anyRequest().authenticated()
                )
                .httpBasic();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}