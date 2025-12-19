package com.soaint.gestion.areas.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soaint.gestion.areas.api.entity.SolicitudUsuarioDetalleEntity;

public interface SolicitudUsuarioDetalleRepository extends JpaRepository<SolicitudUsuarioDetalleEntity, Long> {
    
    List<SolicitudUsuarioDetalleEntity> findByIdSolicitud(UUID idSolicitud);
    
    // 🔴 ERROR ANTERIOR: findByUsername(String username);
    // 🟢 CORRECCIÓN: findByUsuario
    List<SolicitudUsuarioDetalleEntity> findByUsuario(String usuario);
}