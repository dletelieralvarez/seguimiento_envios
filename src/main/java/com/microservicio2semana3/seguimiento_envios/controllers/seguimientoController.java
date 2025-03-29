package com.microservicio2semana3.seguimiento_envios.controllers;

import com.microservicio2semana3.seguimiento_envios.model.Seguimiento;
import com.microservicio2semana3.seguimiento_envios.services.SeguimientoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;  

import java.util.List;

@RestController
@RequestMapping("/seguimiento")

public class seguimientoController {

    private final SeguimientoService seguimientoService;

    public seguimientoController(SeguimientoService seguimientoService) {
        this.seguimientoService = seguimientoService;
    }

    @GetMapping()
    public List<Seguimiento> getTodosLosSeguimientos() {
        return seguimientoService.getRetornaTodosLosSeguimientos();
    }

    @GetMapping("/{id}")
    public Seguimiento getSeguimientoById(@PathVariable String id) {
        return seguimientoService.getRetornaSeguimientoById(id)
                .orElseThrow(() -> 
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró ID de seguimiento N°: " + id)
                );
    }
}