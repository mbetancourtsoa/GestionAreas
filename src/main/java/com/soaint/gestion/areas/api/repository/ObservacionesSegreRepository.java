package com.soaint.gestion.areas.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soaint.gestion.areas.api.entity.ObservacionesSegreEntity;

public interface ObservacionesSegreRepository extends JpaRepository<ObservacionesSegreEntity, Long> {
    List<ObservacionesSegreEntity> findByIdSolicitudOrderByFechaObservacionAsc(UUID idSolicitud);
}
