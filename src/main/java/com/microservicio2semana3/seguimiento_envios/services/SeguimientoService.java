package com.microservicio2semana3.seguimiento_envios.services;

import org.springframework.stereotype.Service;
import com.microservicio2semana3.seguimiento_envios.model.Seguimiento;

import java.util.Arrays;
import java.util.List;  
import java.util.Optional;

@Service
public class SeguimientoService {
    
    private final List<Seguimiento> seguimiento = Arrays.asList(
        new Seguimiento("1", "1001", "En tránsito", "28-03-2025", "23:00", "Argentina", "Centro de distribución Metropolitano", "En proceso de entrega"),
        new Seguimiento("2", "1002", "Entregado", "28-03-2025", "23:10", "Peru", "Balmaceda 510", "Entregado al destinatario"),
        new Seguimiento("3", "1003", "En espera", "28-03-2025", "14:00", "Colombia", "Centro de distribución Colombia", "Esperando confirmación"),
        new Seguimiento("4", "1004", "Cancelado", "28-03-2025", "16:00", "Paraguay", "Aduana de Paraguay", "Pedido cancelado")
    );

    public List<Seguimiento> getRetornaTodosLosSeguimientos() {
        return seguimiento;
    }
    public Optional<Seguimiento> getRetornaSeguimientoById(String id) {
        return seguimiento.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }
}
