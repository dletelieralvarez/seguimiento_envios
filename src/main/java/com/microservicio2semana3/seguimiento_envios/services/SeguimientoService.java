package com.microservicio2semana3.seguimiento_envios.services;
import org.springframework.stereotype.Service;

import com.microservicio2semana3.seguimiento_envios.model.Seguimiento;

import java.util.List;  
import java.util.Optional;

@Service
public interface SeguimientoService {
    List<Seguimiento> getSeguimientos(); 
    Optional<Seguimiento> getSeguimientoById(int id); 
    Seguimiento crearSeguimiento(Seguimiento seguimiento);
    Seguimiento actualizarSeguimiento(Seguimiento seguimiento,int id);
    Seguimiento eliminarSeguimiento(int id);
    List<Seguimiento> buscarPorIdEnvio(int idEnvio);
}
