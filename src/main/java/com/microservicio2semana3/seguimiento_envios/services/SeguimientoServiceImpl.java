package com.microservicio2semana3.seguimiento_envios.services;
import com.microservicio2semana3.seguimiento_envios.repository.SeguimientoEnviosRepository;
import com.microservicio2semana3.seguimiento_envios.model.Seguimiento;

import jakarta.annotation.PostConstruct;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SeguimientoServiceImpl implements SeguimientoService {

   private final SeguimientoEnviosRepository seguimientoEnviosRepository;

   public SeguimientoServiceImpl(SeguimientoEnviosRepository seguimientoEnviosRepository) {
        this.seguimientoEnviosRepository = seguimientoEnviosRepository;
        // inyeccion de dependencias
    }

   @Override
   public List<Seguimiento> getSeguimientos(){
        log.debug("Servicio : getSeguimientos");
        return seguimientoEnviosRepository.findAll();
   }

    @Override
    public Optional<Seguimiento> getSeguimientoById(int id) {
        log.debug("Servicio : getSeguimientoById - ", id);
        return seguimientoEnviosRepository.findById(id);
    }
    
    @Override
    public Seguimiento crearSeguimiento(Seguimiento seguimiento) {
        log.debug("Servicio : crearSeguimiento - ", seguimiento);
        if(seguimientoEnviosRepository.existsById(seguimiento.getId())) {
            log.error("El seguimiento ya existe con el id: ", seguimiento.getId());
            throw new IllegalArgumentException("El seguimiento ya existe con el id: " + seguimiento.getId());   
        }

        return seguimientoEnviosRepository.save(seguimiento);
    }

    @Override
    public Seguimiento actualizarSeguimiento(Seguimiento seguimiento, int id){
        log.debug("Servicio : actualizarSeguimiento()");
        if(!seguimientoEnviosRepository.existsById(id)) {
            log.error("El seguimiento no existe con el id: ", id);
            throw new IllegalArgumentException("El seguimiento no existe con el id: " + id);   
        }

        return seguimientoEnviosRepository.save(seguimiento);
    }

    @Override
    public Seguimiento eliminarSeguimiento(int id) {
        log.debug("Servicio : eliminarSeguimiento()");
       Optional<Seguimiento> seguimiento = seguimientoEnviosRepository.findById(id); 
       if(seguimiento.isPresent()){
            seguimientoEnviosRepository.delete(seguimiento.get());
            return seguimiento.get();
        } else {
            return null;
        }
    }

    @PostConstruct
    public void init() {
        log.debug("Servicio : init(), me indica cuantos registro hay en la base de datos"); 
        System.out.println("Cantidad de eventos: " + seguimientoEnviosRepository.count());
    }

    @Override
    public List<Seguimiento> buscarPorIdEnvio(int idEnvio) {
        log.debug("Servicio : buscarPorIdEnvio - ", idEnvio);
        return seguimientoEnviosRepository.findByIdEnvio(idEnvio);  
    }

}
