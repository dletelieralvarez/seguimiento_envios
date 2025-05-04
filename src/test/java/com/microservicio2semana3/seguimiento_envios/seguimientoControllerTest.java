package com.microservicio2semana3.seguimiento_envios;
import com.microservicio2semana3.seguimiento_envios.config.SecurityConfig;
import com.microservicio2semana3.seguimiento_envios.controllers.SeguimientoController;
import com.microservicio2semana3.seguimiento_envios.model.Seguimiento;
import com.microservicio2semana3.seguimiento_envios.services.SeguimientoService;
import com.microservicio2semana3.seguimiento_envios.hateoas.SeguimientoModelAssembler;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@WebMvcTest(SeguimientoController.class)
@Import(SecurityConfig.class)
@WithMockUser(username = "admin", password = "admin123", roles = { "ADMIN" })
public class seguimientoControllerTest {

    @Autowired
    private MockMvc mockMvc; 
 
    @MockBean
    private SeguimientoService seguimientoService;

    @MockBean
    private SeguimientoModelAssembler seguimientoModelAssembler;      

    @Test
    public void testGetSeguimientoById() throws Exception {
        
        Seguimiento seguimiento = new Seguimiento(1,1010,"En transito", "04/05/2025", "10:00","Peru", "ubicacion 1","es una prueba"); 
        EntityModel<Seguimiento> entityModel = EntityModel.of(seguimiento, 
        linkTo(methodOn(SeguimientoController.class).retornaSeguimientoById(1)).withSelfRel(),
        linkTo(methodOn(SeguimientoController.class).retornaTodosLosSeguimientosDeEnvios()).withRel("Lista de seguimientos"),
        linkTo(methodOn(SeguimientoController.class).crearSeguimiento(seguimiento)).withRel("crea un seguimiento nuevo"), 
        linkTo(methodOn(SeguimientoController.class).actualizarSeguimientoEnvio(1, seguimiento)).withRel("actualiza un seguimiento de envio"), 
        linkTo(methodOn(SeguimientoController.class).eliminarSeguimientoEnvio(1)).withRel("elimina un seguimiento de envio")
        ); 

        when(seguimientoService.getSeguimientoById(1)).thenReturn(java.util.Optional.of(seguimiento));
        when(seguimientoModelAssembler.toModel(seguimiento)).thenReturn(entityModel);

        mockMvc.perform(get("/seguimiento/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.ID").value(1))
                .andExpect(jsonPath("$.data.ID_ENVIO").value(1010))
                .andExpect(jsonPath("$.data.ESTADO").value("En transito"))
                .andExpect(jsonPath("$.data.FECHA").value("04/05/2025"))
                .andExpect(jsonPath("$.data.HORA").value("10:00"))
                .andExpect(jsonPath("$.data.PAIS_DESTINO").value("Peru"))
                .andExpect(jsonPath("$.data.UBICACION").value("ubicacion 1"))
                .andExpect(jsonPath("$.data.OBSERVACIONES").value("es una prueba"))
                //links es un arreglo, por lo que se accederé a los links por su indice, 
                //es como lo agregue en mi apiresult
                .andExpect(jsonPath("$.links[0].href").exists()) 
                .andExpect(jsonPath("$.links[1].href").exists()) 
                .andExpect(jsonPath("$.links[2].href").exists())
                .andExpect(jsonPath("$.links[3].href").exists())
                .andExpect(jsonPath("$.links[4].href").exists())
                .andDo(result -> {
                    // Imprime la respuesta en la consola
                    System.out.println(result.getResponse().getContentAsString());
                });

                //test para pelicula que no existe
                when(seguimientoService.getSeguimientoById(222)).thenReturn(java.util.Optional.empty());
                
                mockMvc.perform(get("/seguimiento/222")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.mensaje").exists())
                        .andExpect(jsonPath("$.codigoEstado").exists())
                        .andDo(result ->{
                            System.out.println(result.getResponse().getContentAsString()); // Imprime la respuesta en consola
                        });
                    }
                    
    @Test
    public void testGetAllSeguimientos() throws Exception {
        Seguimiento seguimiento1 = new Seguimiento(1, 1010, "En tránsito", "04/05/2025", "10:00", "Peru", "ubicacion 1", "observacion");
        Seguimiento seguimiento2 = new Seguimiento(2, 2020, "Entregado", "05/05/2025", "11:00", "Chile", "ubicacion 2", "observacion");

        List<Seguimiento> lista = List.of(seguimiento1, seguimiento2);

        when(seguimientoService.getSeguimientos()).thenReturn(lista);
        when(seguimientoModelAssembler.toModel(any(Seguimiento.class)))
            .thenAnswer(invocation -> {
                Seguimiento s = invocation.getArgument(0);
                return EntityModel.of(s);
        });

        mockMvc.perform(get("/seguimiento")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.mensaje").exists())
            .andExpect(jsonPath("$.codigoEstado").exists());
        }

      /*  
    @Test
    public void testCrearSeguimiento() throws Exception {
        Seguimiento seguimiento = new Seguimiento(1, 1010, "En tránsito", "04/05/2025", "10:00", "Peru", "ubicacion 1", "observacion");
        
        when(seguimientoService.crearSeguimiento(any(Seguimiento.class)))
            .thenReturn(seguimiento);
        when(seguimientoModelAssembler.toModel(any(Seguimiento.class)))
            .thenReturn(EntityModel.of(seguimiento));
        
        String jsonBody = """
        {
                "ID": 1,
                "ID_ENVIO": 1010,
                "ESTADO": "En tránsito",
                "FECHA": "04/05/2025",
                "HORA": "10:00",
                "PAIS_DESTINO": "Peru",
                "UBICACION": "ubicacion 1",
                "OBSERVACIONES": "observacion"
        }
        """;
        
        mockMvc.perform(post("/seguimiento")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonBody))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.data.ID").exists()); 
        }
          */  
/* 
    @Test
    public void testActualizarSeguimiento() throws Exception {
        int id = 1;
        Seguimiento actualizado = new Seguimiento(id, 1010, "Entregado", "04/05/2025", "10:00", "Peru", "ubicacion final", "entregado sin novedades");
        
        when(seguimientoService.crearSeguimiento(any(Seguimiento.class)))
            .thenReturn(actualizado);

        when(seguimientoService.actualizarSeguimiento(any(Seguimiento.class), eq(id)))
            .thenReturn(actualizado);
        when(seguimientoModelAssembler.toModel(any(Seguimiento.class)))
            .thenReturn(EntityModel.of(actualizado));

        String jsonBody = """
        {
            "idEnvio": 1010,
            "estado": "Entregado",
            "fecha": "04/05/2025",
            "hora": "10:00",
            "paisDestino": "Peru",
            "ubicacion": "ubicacion final",
            "observaciones": "entregado sin novedades"
        }
        """;

        mockMvc.perform(put("/seguimiento/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.estado").value("Entregado"));
    }
*/
/*
    @Test
    public void testEliminarSeguimiento() throws Exception {
        int id = 1;

        //doNothing().when(seguimientoService).eliminarSeguimiento(id);

        mockMvc.perform(delete("/seguimiento/{id}", id))
            .andExpect(status().isNoContent());
    }
            */
            
}
