package com.soaint.gestion.areas.api.mapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.soaint.gestion.areas.api.dto.ObservacionDto;
import com.soaint.gestion.areas.api.dto.SolicitudObjetoDetalleDto;
import com.soaint.gestion.areas.api.dto.SolicitudSeguridadCreateDto;
import com.soaint.gestion.areas.api.dto.SolicitudSeguridadDto;
import com.soaint.gestion.areas.api.dto.SolicitudUsuarioDetalleDto;
import com.soaint.gestion.areas.api.entity.ObservacionesSegreEntity;
import com.soaint.gestion.areas.api.entity.SolicitudObjetoDetalleEntity;
import com.soaint.gestion.areas.api.entity.SolicitudSeguridadEntity;
import com.soaint.gestion.areas.api.entity.SolicitudUsuarioDetalleEntity;

public final class SolicitudSeguridadMapper {

    private SolicitudSeguridadMapper() {}

    /* ============ TO DTO (SALIDA) ============ */

    public static SolicitudSeguridadDto toDto(
            SolicitudSeguridadEntity e,
            List<SolicitudObjetoDetalleEntity> objs,
            List<SolicitudUsuarioDetalleEntity> usrs,
            List<ObservacionesSegreEntity> obs) {
        
        // ... (Esta parte estaba bien, déjala igual) ...
        SolicitudSeguridadDto dto = new SolicitudSeguridadDto();
        dto.setIdSolicitud(e.getIdSolicitud());
        dto.setAreaSolicitante(e.getAreaSolicitante());
        dto.setTipoSolicitud(e.getTipoSolicitud());
        dto.setOficinaProductora(e.getOficinaProductora());
        dto.setSerieDocumental(e.getSerieDocumental());
        dto.setSubSerieDocumental(e.getSubSerieDocumental());
        dto.setNombreExpediente(e.getNombreExpediente());
        dto.setAccesoHasta(e.getAccesoHasta());
        dto.setEstado(e.getEstado());
        dto.setUsuarioSolicitante(e.getUsuarioSolicitante());
        dto.setFechaSolicitud(e.getFechaSolicitud());

        if (objs != null) {
            dto.setObjetos(objs.stream().map(SolicitudSeguridadMapper::toDto).collect(Collectors.toList()));
        }
        if (usrs != null) {
            dto.setUsuarios(usrs.stream().map(SolicitudSeguridadMapper::toDto).collect(Collectors.toList()));
        }
        if (obs != null) {
            dto.setObservaciones(obs.stream().map(SolicitudSeguridadMapper::toDto).collect(Collectors.toList()));
        }
        return dto;
    }

    // CORRECCIÓN 1: Mapear el ID del objeto
    public static SolicitudObjetoDetalleDto toDto(SolicitudObjetoDetalleEntity e) {
        SolicitudObjetoDetalleDto d = new SolicitudObjetoDetalleDto();
        d.setId(e.getId());
        d.setTipoObjeto(e.getTipoObjeto());
        
        // CAMBIO IMPORTANTE: Usamos getIdObjeto() (que mapea a la columna IdentificadorObjeto)
        // para llenar el campo identificadorObjeto del JSON
        d.setIdentificadorObjeto(e.getIdObjeto()); 
        
        d.setNombreObjeto(e.getNombreObjeto());
        d.setClase(e.getClase());
        d.setSensibilidad(e.getSensibilidad());
        d.setObservacion(e.getObservacion());
        return d;
    }

    // CORRECCIÓN 2: Mapear fecha y nombre del usuario
    public static SolicitudUsuarioDetalleDto toDto(SolicitudUsuarioDetalleEntity e) {
        SolicitudUsuarioDetalleDto d = new SolicitudUsuarioDetalleDto();
        d.setId(e.getId());
        // d.setIdUsuario(e.getIdUsuario()); // Si usas UUID, descomenta
        d.setUsername(e.getUsuario()); // Recuerda: en entidad es 'usuario'
        
        // MAPEO NUEVO
        d.setNombre(e.getNombreUsuario());
        d.setFechaHasta(e.getFechaHasta());

        d.setAutorizado(e.getAutorizado());
        d.setFechaAutorizacion(e.getFechaAutorizacion());
        d.setUsuarioAutorizador(e.getUsuarioAutorizador());
        return d;
    }

    // ... (El resto de métodos toEntity y toDto Observación déjalos igual) ...
    public static ObservacionDto toDto(ObservacionesSegreEntity e) {
        ObservacionDto d = new ObservacionDto();
        d.setId(e.getId());
        d.setUsername(e.getUsername());
        d.setObservacion(e.getObservacion());
        d.setFechaObservacion(e.getFechaObservacion());
        return d;
    }

    // ... (Métodos toEntity) ...
    public static SolicitudSeguridadEntity toEntity(SolicitudSeguridadCreateDto d, String usuarioSolicitante) {
        SolicitudSeguridadEntity e = new SolicitudSeguridadEntity();
        e.setIdSolicitud(UUID.randomUUID());
        e.setAreaSolicitante(d.getAreaSolicitante());
        e.setTipoSolicitud(d.getTipoSolicitud());
        e.setOficinaProductora(d.getOficinaProductora());
        e.setNombreExpediente(d.getNombreExpediente());
        e.setUsuarioSolicitante(usuarioSolicitante);
        return e;
    }

    public static SolicitudObjetoDetalleEntity toEntity(UUID idSolicitud, SolicitudObjetoDetalleDto d) {
        SolicitudObjetoDetalleEntity e = new SolicitudObjetoDetalleEntity();
        e.setIdSolicitud(idSolicitud);
        e.setTipoObjeto(d.getTipoObjeto());
        e.setNombreObjeto(d.getNombreObjeto());
        return e;
    }

    public static SolicitudUsuarioDetalleEntity toEntity(UUID idSolicitud, String username) {
        SolicitudUsuarioDetalleEntity e = new SolicitudUsuarioDetalleEntity();
        e.setIdSolicitud(idSolicitud);
        e.setUsuario(username);
        e.setAutorizado(true);
        return e;
    }

    public static ObservacionesSegreEntity toEntity(UUID idSolicitud, String username, String obs) {
        ObservacionesSegreEntity e = new ObservacionesSegreEntity();
        e.setIdSolicitud(idSolicitud);
        e.setUsername(username);
        e.setObservacion(obs);
        return e;
    }
}