package com.microservicio2semana3.seguimiento_envios;
import com.microservicio2semana3.seguimiento_envios.model.Seguimiento;
import com.microservicio2semana3.seguimiento_envios.repository.SeguimientoEnviosRepository;
import com.microservicio2semana3.seguimiento_envios.services.SeguimientoServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SeguimientoServiceTest {
   @Mock
   private SeguimientoEnviosRepository seguimientoEnviosRepository; //mock del repositorio
   @InjectMocks
   private SeguimientoServiceImpl seguimientoService; //instancia del servicio

   @BeforeEach
    public void setUp() { //inicializa los mocks antes de cada prueba
         MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetSeguimientos() { //prueba del metodo getSeguimientos
        Seguimiento seguimiento1 = new Seguimiento(1, 1010, "En transito", "04/05/2025", "10:00", "Peru", "ubicacion 1", "es una prueba");
        Seguimiento seguimiento2 = new Seguimiento(2, 1011, "Entregado", "05/05/2025", "11:00", "Chile", "ubicacion 2", "es una prueba 2");
        List<Seguimiento> seguimientos = Arrays.asList(seguimiento1, seguimiento2);

        when(seguimientoEnviosRepository.findAll()).thenReturn(seguimientos); 
        //cuando se llame al metodo findAll del repositorio, devuelve la lista de seguimientos

        List<Seguimiento> result = seguimientoService.getSeguimientos(); 
        //llama al metodo getSeguimientos del servicio

        assertEquals(2, result.size()); 
        //verifica que el tamaño de la lista sea 2
        assertEquals("En transito", result.get(0).getEstado()); 
        //verifica que el primer
    }

    @Test
    public void testGetSeguimientoById() { //prueba del metodo getSeguimientoById
        int id = 1;
        Seguimiento seguimiento = new Seguimiento(id, 1010, "En transito", "04/05/2025", "10:00", "Peru", "ubicacion 1", "es una prueba");

        when(seguimientoEnviosRepository.findById(id)).thenReturn(Optional.of(seguimiento)); 
        //cuando se llame al metodo findById del repositorio, devuelve el seguimiento

        Optional<Seguimiento> result = seguimientoService.getSeguimientoById(id); 
        //llama al metodo getSeguimientoById del servicio

        assertTrue(result.isPresent()); 
        //verifica que el resultado no sea vacio
    }

    @Test
    public void testCrearSeguimiento() { //prueba del metodo crearSeguimiento
        Seguimiento seguimiento = new Seguimiento(1, 1010, "En transito", "04/05/2025", "10:00", "Peru", "ubicacion 1", "es una prueba");

        when(seguimientoEnviosRepository.save(any(Seguimiento.class))).thenReturn(seguimiento); 
        //cuando se llame al metodo save del repositorio, devuelve el seguimiento

        Seguimiento result = seguimientoService.crearSeguimiento(seguimiento); 
        //llama al metodo crearSeguimiento del servicio

        assertEquals("En transito", result.getEstado()); 
        //verifica que el estado sea "En transito"
    }
/* 
    @Test
    public void testActualizarSeguimiento() { //prueba del metodo actualizarSeguimiento
        int id = 1;
        Seguimiento seguimiento = new Seguimiento(id, 1010, "En transito", "04/05/2025", "10:00", "Peru", "ubicacion 1", "es una prueba");

        when(seguimientoEnviosRepository.findById(id)).thenReturn(Optional.of(seguimiento)); 
        //cuando se llame al metodo findById del repositorio, devuelve el seguimiento

        when(seguimientoEnviosRepository.save(any(Seguimiento.class))).thenReturn(seguimiento); 
        //cuando se llame al metodo save del repositorio, devuelve el seguimiento

        Seguimiento result = seguimientoService.actualizarSeguimiento(seguimiento, id); 
        //llama al metodo actualizarSeguimiento del servicio

        assertEquals("En transito", result.getEstado()); 
        //verifica que el estado sea "En transito"
    }
*/
    @Test
    public void testEliminarSeguimiento() { //prueba del metodo eliminarSeguimiento
        int id = 1;
        Seguimiento seguimiento = new Seguimiento(id, 1010, "En transito", "04/05/2025", "10:00", "Peru", "ubicacion 1", "es una prueba");

        when(seguimientoEnviosRepository.findById(id)).thenReturn(Optional.of(seguimiento)); 
        //cuando se llame al metodo findById del repositorio, devuelve el seguimiento

        doNothing().when(seguimientoEnviosRepository).deleteById(id); 
        //cuando se llame al metodo deleteById del repositorio, no hace nada

        Seguimiento result = seguimientoService.eliminarSeguimiento(id); 
        //llama al metodo eliminarSeguimiento del servicio

        assertEquals("En transito", result.getEstado()); 
        //verifica que el estado sea "En transito"
    }
    @Test   
    public void testBuscarPorIdEnvio() { //prueba del metodo buscarPorIdEnvio
        int idEnvio = 1010;
        Seguimiento seguimiento1 = new Seguimiento(1, idEnvio, "En transito", "04/05/2025", "10:00", "Peru", "ubicacion 1", "es una prueba");
        when(seguimientoEnviosRepository.findByIdEnvio(idEnvio))
            .thenReturn(Arrays.asList(seguimiento1));
        List<Seguimiento> result = seguimientoService.buscarPorIdEnvio(idEnvio);
        assertEquals(1, result.size()); //verifica que el tamaño de la lista sea 1
        assertEquals("En transito", result.get(0).getEstado()); //verifica que el estado sea "En transito"
        }
        
}
