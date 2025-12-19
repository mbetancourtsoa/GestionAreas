package com.soaint.gestion.areas.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soaint.gestion.areas.api.entity.SolicitudObjetoDetalleEntity;

public interface SolicitudObjetoDetalleRepository extends JpaRepository<SolicitudObjetoDetalleEntity, Long> {
    List<SolicitudObjetoDetalleEntity> findByIdSolicitud(UUID idSolicitud);
    List<SolicitudObjetoDetalleEntity> findByIdSolicitudAndTipoObjeto(UUID idSolicitud, String tipoObjeto);
}
