package com.soaint.gestion.areas.api.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors; 

import org.springframework.stereotype.Service;

import com.soaint.gestion.areas.api.exception.NotFoundException;
import com.soaint.gestion.areas.api.repository.ConstantesRepository;

import lombok.RequiredArgsConstructor;
import lombok.var;

@Service
@RequiredArgsConstructor
public class ConstantesService {

    private final ConstantesRepository repo;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /** Devuelve SOLO los NOMBREs (no códigos) activos = 1, ordenados por nombre */
    public List<String> nombresPorParent(String parentId) {
        var list = repo.findByParentIdAndActivoOrderByNombreAsc(parentId, 1);
        if (list == null || list.isEmpty()) {
            throw new NotFoundException("ERR_CONSTANTES_NO_ENCONTRADAS",
                    "No hay constantes activas para parentId=" + parentId);
        }
        return list.stream()
                   .map(c -> c.getNombre())
                   .collect(Collectors.toList()); // 👈 en vez de toList()
    }

    public String nowString() {
        return LocalDateTime.now().format(FMT);
    }
}
