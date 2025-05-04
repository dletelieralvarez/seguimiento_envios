package com.microservicio2semana3.seguimiento_envios.hateoas;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*; // Importa funciones para generar enlaces
import com.microservicio2semana3.seguimiento_envios.controllers.seguimientoController;
import com.microservicio2semana3.seguimiento_envios.model.Seguimiento;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class SeguimientoModelAssembler implements RepresentationModelAssembler<Seguimiento, EntityModel<Seguimiento>> {

    @Override
    public @NonNull EntityModel<Seguimiento> toModel(@NonNull Seguimiento seguimiento) {
        return EntityModel.of(seguimiento,
                linkTo(methodOn(seguimientoController.class).retornaSeguimientoById(seguimiento.getId())).withSelfRel(),
                linkTo(methodOn(seguimientoController.class).retornaTodosLosSeguimientosDeEnvios()).withRel("all"),
                linkTo(methodOn(seguimientoController.class).crearSeguimiento(seguimiento)).withRel("create"),
                linkTo(methodOn(seguimientoController.class).eliminarSeguimientoEnvio(seguimiento.getId())).withRel("delete"),
                linkTo(methodOn(seguimientoController.class).actualizarSeguimientoEnvio(seguimiento.getId(), seguimiento)).withRel("update"));
    }
    
}