package com.gym.gymapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disabled for testing/local dev
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/index.html", "/js/**", "/css/**").permitAll() // Let anyone see the UI
                        .requestMatchers("/api/gym/members/add", "/api/gym/members/all").hasRole("ADMIN")
                        .anyRequest().authenticated() // Everything else needs a login
                )
                .formLogin(withDefaults()) // Enables the default login page
                .httpBasic(withDefaults()); // Allows Postman/API testing

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        // Equivalent to your Admin credentials from GymApp.java
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin123")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }
}