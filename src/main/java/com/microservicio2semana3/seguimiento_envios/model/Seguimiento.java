package com.microservicio2semana3.seguimiento_envios.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Seguimiento {
    private String id;
    private String idEnvio;
    private String estado;
    private String fecha;
    private String hora;
    private String paisDestino; 
    private String ubicacion;
    private String observaciones;
}