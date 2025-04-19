package com.microservicio2semana3.seguimiento_envios.model;
import com.microservicio2semana3.seguimiento_envios.validation.FechaValida;
import com.microservicio2semana3.seguimiento_envios.validation.HoraValida;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column; 
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;

@Data //genera los get y set 
@AllArgsConstructor //constructor vacio
@NoArgsConstructor //constructor con los atributos
@Entity //indica que es una entidad de la base de datos
@Table(name = "SEGUIMIENTO") //nombre de la tabla en la base de datos
public class Seguimiento {

    @Id //indica que es la llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //indica que es autoincrementable
    @Column(name = "ID") //nombre de la columna en la base de datos
    @JsonProperty("ID") //nombre del atributo en el json
    private int Id; //id del seguimiento

    @NotNull(message = "El id del envío no puede estar vacío")
    @Column(name = "ID_ENVIO") //nombre de la columna en la base de datos
    @JsonProperty("ID_ENVIO") //nombre del atributo en el json
    private int idEnvio; //id del envio

    @NotBlank(message = "El estado no puede estar vacio") //validacion de que no este vacio
    @Size(min = 1, max = 100, message = "El estado debe tener entre 1 y 100 caracteres") //validacion de que tenga entre 3 y 50 caracteres
    @Column(name = "ESTADO") //nombre de la columna en la base de datos
    @JsonProperty("ESTADO") //nombre del atributo en el json
    private String estado;  //estado del envio


    @NotBlank(message = "La fecha no puede estar vacia") //validacion de que no este vacio
    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "La fecha debe tener el formato dd/MM/yyyy") //validacion de que tenga el formato dd/MM/yyyy
    @Column(name = "FECHA") //nombre de la columna en la base de datos
    @JsonProperty("FECHA") //nombre del atributo en el json
    @FechaValida //validacion de la fecha
    private String fecha;   //fecha del seguimiento

    @NotBlank(message = "La hora no puede estar vacia") //validacion de que no este vacio
    @HoraValida //validacion de la hora
    @Column(name = "HORA") //nombre de la columna en la base de datos
    @JsonProperty("HORA") //nombre del atributo en el json    
    private String hora;    //hora del seguimiento    

    @NotBlank(message = "El pais de destino no puede estar vacio") //validacion de que no este vacio
    @Size(min = 1, max = 100, message = "El pais de destino debe tener entre 1 y 100 caracteres") //validacion de que tenga entre 3 y 50 caracteres 
    @Column(name = "PAIS_DESTINO") //nombre de la columna en la base de datos
    @JsonProperty("PAIS_DESTINO") //nombre del atributo en el json    
    private String paisDestino; //pais de destino

    @NotBlank(message = "Ubicación no puede estar vacia") //validacion de que no este vacio
    @Size(min = 1, max = 150, message = "La ubicacion debe tener entre 1 y 150 caracteres") //validacion de que tenga entre 3 y 50 caracteres
    @Column(name = "UBICACION") //nombre de la columna en la base de datos
    @JsonProperty("UBICACION") //nombre del atributo en el json
    private String ubicacion;  //ubicacion del envio

    @NotBlank(message = "Observaciones no puede estar vacio") //validacion de que no este vacio
    @Size(min = 1, max = 300, message = "Observacion debe tener entre 1 y 300 caracteres") //validacion de que tenga entre 3 y 50 caracteres
    @Column(name = "OBSERVACIONES") //nombre de la columna en la base de datos
    @JsonProperty("OBSERVACIONES") //nombre del atributo en el json
    private String observaciones; //observaciones del envio
}