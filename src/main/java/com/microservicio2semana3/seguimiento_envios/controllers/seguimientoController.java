package com.microservicio2semana3.seguimiento_envios.controllers;
import com.microservicio2semana3.seguimiento_envios.model.ApiResult; 
import com.microservicio2semana3.seguimiento_envios.model.Seguimiento;
import com.microservicio2semana3.seguimiento_envios.services.SeguimientoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;  

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Slf4j
@RestController
@RequestMapping("/seguimiento")
public class seguimientoController {

    @Autowired
    //private final SeguimientoService seguimientoService;
    private SeguimientoService seguimientoService;

    //public seguimientoController(SeguimientoService seguimientoService) {
    //    this.seguimientoService = seguimientoService;
    //}

    @GetMapping()
    public ResponseEntity<ApiResult<List<Seguimiento>>> retornaTodosLosSeguimientosDeEnvios() {
        try{
            log.info("Get / seguimiento - Retorna todos los seguimientos de envios");
            List<Seguimiento> seguimientos = seguimientoService.getSeguimientos();
            if(seguimientos.isEmpty()){
                log.warn("No se encontraron seguimientos de envios");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResult<>("No se encontraron seguimientos de envios", null, HttpStatus.NOT_FOUND.value()));
            }

            return ResponseEntity.ok(new ApiResult<>("Lista de Seguimientos de envios encontrados : ", seguimientos, HttpStatus.OK.value()));   
        }
        catch (Exception e) {
            log.error("Error al obtener los seguimientos de envios: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResult<>("Error al obtener los seguimientos de envios", null,  HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<Seguimiento>> retornaSeguimientoById(@PathVariable int id) {
        try{
            log.info("Get / seguimiento/{} - Retorna seguimiento de envios por id", id);
            Optional<Seguimiento> seguimiento = seguimientoService.getSeguimientoById(id);
            if(seguimiento.isPresent()){
                log.info("Seguimiento de envio encontrado con id: {}", id);
                return ResponseEntity.ok(new ApiResult<>("Seguimiento de envios encontrado : ", seguimiento.get(), HttpStatus.OK.value())); 
            }
            else{
                log.warn("No se encontro seguimiento de envio con id: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResult<>("No se encontro seguimiento de envio con id: " + id, null, HttpStatus.NOT_FOUND.value()));
            }
            
        }
        catch(Exception e) {
            log.error("Error al obtener el seguimiento de envios por id: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResult<>("Error al obtener el seguimiento de envios por id", null,  HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResult<?>> crearSeguimiento(@Valid @RequestBody Seguimiento seguimiento){
        try{
            log.info("Post / seguimiento - se crea un nuevo seguimiento de envios");
            Seguimiento nuevoSeguimiento = seguimientoService.crearSeguimiento(seguimiento);

            ApiResult<List<Seguimiento>> respuesta = new ApiResult<>(
                "Seguimiento de envios creado con exito", 
                List.of(nuevoSeguimiento), 
                HttpStatus.CREATED.value()
            );  

            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        }
        catch (Exception e) {
            ApiResult<Object> error = new ApiResult<>(
                "Error al crear el seguimiento de envios: " + e.getMessage(), 
                null, 
                HttpStatus.INTERNAL_SERVER_ERROR.value()
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<List<Seguimiento>>> actualizarSeguimientoEnvio(@PathVariable int id, @Valid @RequestBody Seguimiento seguimiento){
        try{
            log.info("Put / seguimiento - Se actualiza el seguimiento de envios con id : " + id);
            Optional<Seguimiento> buscarSeguimiento = seguimientoService.getSeguimientoById(id); 

            if(!buscarSeguimiento.isPresent()){
                log.warn("No se encontro seguimiento de envio con id:" + id); 
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResult<>("Seguimiento de envios no encontrado con id : " + id, null, HttpStatus.NOT_FOUND.value()));
            }

            //si el seguimiento existe, se actualiza
            Seguimiento seguimientoEnvio = seguimientoService.getSeguimientoById(id).get();
            seguimientoEnvio.setIdEnvio(seguimiento.getIdEnvio());
            seguimientoEnvio.setEstado(seguimiento.getEstado());
            seguimientoEnvio.setFecha(seguimiento.getFecha());
            seguimientoEnvio.setHora(seguimiento.getHora());
            seguimientoEnvio.setPaisDestino(seguimiento.getPaisDestino());
            seguimientoEnvio.setUbicacion(seguimiento.getUbicacion());
            seguimientoEnvio.setObservaciones(seguimiento.getObservaciones());
            //guardar el seguimiento actualizado
            Seguimiento seguimientoActualizado = seguimientoService.actualizarSeguimiento(seguimientoEnvio, id); 
            //retornar el resultado con APIResult
            return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResult<>("Seguimiento de envios actualizado con exito", List.of(seguimientoActualizado), HttpStatus.OK.value()));
        }
        catch(Exception e){
            //en caso de error, retornar el error con APIResult
            log.error("Error al actualizar seguimiento de envios con id : " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResult<>("Error al actualizar seguimiento de envios con id : " + id, null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<String>> eliminarSeguimientoEnvio(@PathVariable int id){
        try{
            log.info("Delete / seguimiento - se elimina el seguimiento de envio con id : " + id); 
            //retornar los datos del seguimiento a eliminar
            Optional<Seguimiento> seguimiento = seguimientoService.getSeguimientoById(id);
            if(!seguimiento.isPresent()){
                log.warn("No se encontro el seguimiento de envio con id : " + id); 
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResult<>("No se encontro el seguimiento de envio con id : " + id, null, HttpStatus.NOT_FOUND.value())); 
            }

            //si encuentra el seguimiento, se elimina
            log.info("Seguimiento de envio ENCONTRADO con id : " + id);
            seguimientoService.eliminarSeguimiento(id);
            return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResult<>("Seguimiento de envios eliminado con exito", "ID de seguimiento eliminado : " + id, HttpStatus.OK.value()));
        }
        catch(Exception ex){
            log.error("Error al eliminar seguimiento de envios con id : " + id, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResult<>("Error al eliminar seguimiento de envios con id : " + id, null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<ApiResult<List<Seguimiento>>> buscarSeguimientoPorIdEnvio(@RequestParam int idEnvio) {
        try {
            log.info("Get / seguimiento/buscar - se busca el seguimiento de envios por ID Envio : " + idEnvio);
            List<Seguimiento> resultados = seguimientoService.buscarPorIdEnvio(idEnvio);
            
            if(resultados.isEmpty()){
                log.warn("No se encontraron seguimientos con el ID de Envio : " + idEnvio);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResult<>("No se encontraron seguimientos con el ID de Envio : " + idEnvio, null, HttpStatus.NOT_FOUND.value()));   
            }
            
            return ResponseEntity.ok(new ApiResult<>("Seguimientos encontrados con el ID de Envio", resultados, HttpStatus.OK.value())); 
               
        } catch (Exception e) {
           log.error("Error al buscar seguimiento de envios por ID Envio : " + idEnvio, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResult<>("Error al buscar seguimiento de envios por ID Envio: " + idEnvio, null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }


}