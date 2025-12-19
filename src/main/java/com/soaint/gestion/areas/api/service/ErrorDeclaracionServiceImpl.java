package com.soaint.gestion.areas.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soaint.gestion.areas.api.domain.Constantes;
import com.soaint.gestion.areas.api.domain.Declaracion;
import com.soaint.gestion.areas.api.domain.ErrorDeclaracion;
import com.soaint.gestion.areas.api.dto.ErrorDeclaracionDto;
import com.soaint.gestion.areas.api.dto.ErrorDeclaracionRequest;
import com.soaint.gestion.areas.api.repository.ConstantesRepository;
import com.soaint.gestion.areas.api.repository.DeclaracionRepository;
import com.soaint.gestion.areas.api.repository.ErrorDeclaracionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ErrorDeclaracionServiceImpl implements ErrorDeclaracionService {

    private final ErrorDeclaracionRepository errorRepo;
    private final DeclaracionRepository   declaracionRepo;
    private final ConstantesRepository    constantesRepo;
    private final DeclaracionService      declaracionService;   // para reintentos

    /* ---------------------- 1) Listar ---------------------- */
    @Override
    @Transactional(readOnly = true)
    public List<ErrorDeclaracionDto> listarPorRango(LocalDateTime desde, LocalDateTime hasta) {
        return errorRepo.findByFechaErrorBetween(desde, hasta)
                        .stream()
                        .map(this::toDto)
                        .collect(Collectors.toList());      // Java 8 compatible
    }

    /* ---------------------- 2) Reintentar ------------------ */
    @Override
    public void reintentarError(Integer idError) {
        ErrorDeclaracion err = errorRepo.findById(idError)
                .orElseThrow(EntityNotFoundException::new);
        declaracionService.reintentar(err.getDeclaracion().getId());
    }

    /* ---------------------- 3) Crear nuevo error ----------- */
    @Override
    public ErrorDeclaracionDto crearError(ErrorDeclaracionRequest req) {

        Declaracion decl = declaracionRepo.findById(req.getIdDeclaracion())
                .orElseThrow(() -> new EntityNotFoundException("Declaracion no existe"));

        // valida que el código exista en la tabla Constantes
        Constantes cte = constantesRepo.findByCodigo(req.getTipoError().trim())
                .orElseThrow(() -> new IllegalArgumentException("TipoError inválido"));

        ErrorDeclaracion err = new ErrorDeclaracion();
        err.setDeclaracion(decl);
        err.setTipoError(cte.getCodigo());
        err.setMensajeError(req.getMensajeError());
        err.setTraceId(req.getTraceId());
        err.setFechaError(LocalDateTime.now());

        ErrorDeclaracion saved = errorRepo.save(err);
        return toDto(saved);
    }

    /* ---------------------- helper mapper ------------------ */
    private ErrorDeclaracionDto toDto(ErrorDeclaracion e) {
        ErrorDeclaracionDto dto = new ErrorDeclaracionDto();
        dto.setIdError(e.getId());
        dto.setIdDeclaracion(e.getDeclaracion().getId());
        dto.setTipoError(e.getTipoError());
        dto.setMensajeError(e.getMensajeError());
        dto.setFechaError(e.getFechaError());
        return dto;
    }
}
