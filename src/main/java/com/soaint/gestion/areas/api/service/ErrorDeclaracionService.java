package com.soaint.gestion.areas.api.service;

import java.time.LocalDateTime;
import java.util.List;

import com.soaint.gestion.areas.api.dto.ErrorDeclaracionDto;
import com.soaint.gestion.areas.api.dto.ErrorDeclaracionRequest;

public interface ErrorDeclaracionService {

    List<ErrorDeclaracionDto> listarPorRango(LocalDateTime desde, LocalDateTime hasta);

    void reintentarError(Integer idError);
    
    ErrorDeclaracionDto crearError(ErrorDeclaracionRequest request);
}