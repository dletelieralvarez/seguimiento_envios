package com.microservicio2semana3.seguimiento_envios.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      return http
            .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para simplificar el ejemplo
            .authorizeHttpRequests(auth -> auth .anyRequest().permitAll()) // Permitir todas las solicitudes 
            .httpBasic(Customizer.withDefaults()) // Habilitar autenticación básica (opcional)
            .build();    
    }
}
