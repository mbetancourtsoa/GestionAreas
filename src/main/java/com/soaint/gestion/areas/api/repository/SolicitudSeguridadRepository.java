package com.soaint.gestion.areas.api.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soaint.gestion.areas.api.entity.SolicitudSeguridadEntity;

public interface SolicitudSeguridadRepository extends JpaRepository<SolicitudSeguridadEntity, UUID> {
    List<SolicitudSeguridadEntity> findByEstado(String estado);
    List<SolicitudSeguridadEntity> findByUsuarioSolicitante(String usuarioSolicitante);
    List<SolicitudSeguridadEntity> findByAccesoHastaBefore(LocalDateTime fecha);
}
