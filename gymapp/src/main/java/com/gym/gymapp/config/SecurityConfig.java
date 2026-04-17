package com.gym.gymapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        // Allow static resources AND the login/public endpoints
                        .requestMatchers("/", "/index.html", "/static/**", "/css/**", "/js/**", "/api/gym/trainers/login").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.disable())
                .httpBasic(basic -> {});

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // UPDATED: Added more allowed origins for easier testing
        config.setAllowedOrigins(Arrays.asList(
                "https://tanisha-0902.github.io",
                "https://gym-app-47sf.onrender.com",
                "http://localhost:8080",
                "http://127.0.0.1:5500" // For VS Code Live Server
        ));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        // More stable way to define the admin for Docker
        UserDetails admin = User.withUsername("admin")
                .password("{noop}admin123") // '{noop}' tells Spring it's plain text for now
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }
}