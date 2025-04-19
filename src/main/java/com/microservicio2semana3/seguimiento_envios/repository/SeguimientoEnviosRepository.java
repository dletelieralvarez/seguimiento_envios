package com.microservicio2semana3.seguimiento_envios.repository;
import com.microservicio2semana3.seguimiento_envios.model.Seguimiento;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeguimientoEnviosRepository extends JpaRepository<Seguimiento, Integer> {
    List<Seguimiento> findByIdEnvio(int idEnvio); 
} 
