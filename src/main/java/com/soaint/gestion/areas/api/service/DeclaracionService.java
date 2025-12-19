package com.soaint.gestion.areas.api.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.soaint.gestion.areas.api.domain.Declaracion;
import com.soaint.gestion.areas.api.dto.DeclaracionRequest;

public interface DeclaracionService {
    Declaracion registrarEventoDocumento(DeclaracionRequest dto);

    void procesarPendientes();           // dispara el lote
    void reintentar(Integer idDeclaracion);
    
    
    /* ---------- interface DeclaracionService ---------- */
    List<Declaracion> obtenerPendientes();      // nuevo
    
    Page<Declaracion> obtenerPendientes(Pageable pageable);
    
    Page<Declaracion> obtenerErrores(Pageable pageable);
    
    void declararSeleccionados(List<Integer> ids);


    
    


    
}