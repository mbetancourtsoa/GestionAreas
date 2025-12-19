package com.soaint.gestion.areas.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soaint.gestion.areas.api.entity.InstanciaProcesoEntity;

public interface InstanciaProcesoRepository extends JpaRepository<InstanciaProcesoEntity, UUID> {
    List<InstanciaProcesoEntity> findByIdSolicitud(UUID idSolicitud);
    List<InstanciaProcesoEntity> findByEstado(String estado);
}
