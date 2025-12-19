package com.soaint.gestion.areas.api.service;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soaint.gestion.areas.api.dto.ObservacionDto;
import com.soaint.gestion.areas.api.dto.SolicitudObjetoDetalleDto;
import com.soaint.gestion.areas.api.dto.SolicitudSeguridadCreateDto;
import com.soaint.gestion.areas.api.dto.SolicitudSeguridadDto;
import com.soaint.gestion.areas.api.dto.SolicitudUsuarioCreateDto;
import com.soaint.gestion.areas.api.dto.SolicitudUsuarioDetalleDto;
import com.soaint.gestion.areas.api.entity.InstanciaProcesoEntity;
import com.soaint.gestion.areas.api.entity.ObservacionesSegreEntity;
import com.soaint.gestion.areas.api.entity.SolicitudObjetoDetalleEntity;
import com.soaint.gestion.areas.api.entity.SolicitudSeguridadEntity;
import com.soaint.gestion.areas.api.entity.SolicitudUsuarioDetalleEntity;
import com.soaint.gestion.areas.api.entity.TareasProcesoEntity;
import com.soaint.gestion.areas.api.mapper.SolicitudSeguridadMapper;
import com.soaint.gestion.areas.api.repository.InstanciaProcesoRepository;
import com.soaint.gestion.areas.api.repository.ObservacionesSegreRepository;
import com.soaint.gestion.areas.api.repository.SolicitudObjetoDetalleRepository;
import com.soaint.gestion.areas.api.repository.SolicitudSeguridadRepository;
import com.soaint.gestion.areas.api.repository.SolicitudUsuarioDetalleRepository;
import com.soaint.gestion.areas.api.repository.TareasProcesoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeguridadSegregadaService {

    private final SolicitudSeguridadRepository solicitudRepo;
    private final SolicitudObjetoDetalleRepository objetoRepo;
    private final SolicitudUsuarioDetalleRepository usuarioDetRepo;
    private final ObservacionesSegreRepository observacionRepo;
    private final InstanciaProcesoRepository instanciaRepo;
    private final TareasProcesoRepository tareasRepo;

    /* ============ CREAR SOLICITUD ============ */
    @Transactional
    public SolicitudSeguridadDto crearSolicitud(SolicitudSeguridadCreateDto in, String usuarioSolicitante) {
        
        // 1. Guardar Cabecera
        SolicitudSeguridadEntity cab = SolicitudSeguridadMapper.toEntity(in, usuarioSolicitante);
        
        // Estado inicial según tu diagrama de flujo
        cab.setEstado("EN_REVISION"); 
        cab.setFechaSolicitud(LocalDateTime.now());
        cab = solicitudRepo.save(cab);

        final UUID idSolicitud = cab.getIdSolicitud();

        // 2. Guardar el Objeto Solicitado (CORREGIDO: No usamos getObjetos(), creamos uno solo)
        SolicitudObjetoDetalleEntity objEnt = new SolicitudObjetoDetalleEntity();
        objEnt.setIdSolicitud(idSolicitud);
        objEnt.setNombreObjeto(in.getNombreExpediente()); // Viene del DTO plano
        objEnt.setTipoObjeto(in.getTipoSolicitud());      // "expediente" o "documento"
        objEnt.setIdObjeto(in.getSerieSubserie());        // El ID de la serie/subserie
        // Valores por defecto
        objEnt.setClase("CONFIDENCIAL"); 
        objEnt.setSensibilidad("ALTA");
        
        objetoRepo.save(objEnt); // <--- Guardamos el único objeto


        // 3. Guardar Usuarios
        if (in.getUsuarios() != null && !in.getUsuarios().isEmpty()) {
            List<SolicitudUsuarioDetalleEntity> usuariosEnt = new ArrayList<>();
            
            for (SolicitudUsuarioCreateDto userDto : in.getUsuarios()) {
                SolicitudUsuarioDetalleEntity u = new SolicitudUsuarioDetalleEntity();
                u.setIdSolicitud(idSolicitud);
                // Asegúrate que tu entidad SolicitudUsuarioDetalleEntity tenga 'usuario' y 'nombreUsuario'
                u.setUsuario(userDto.getUsuario());       
                u.setNombreUsuario(userDto.getNombre());  
                
                // Conversión de Date (Angular) a LocalDateTime (Java)
                if (userDto.getFechaHasta() != null) {
                    LocalDateTime ldt = userDto.getFechaHasta().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();
                    u.setFechaHasta(ldt);
                }
                
                u.setAutorizado(false); 
                usuariosEnt.add(u);
            }
            usuarioDetRepo.saveAll(usuariosEnt);
        }

        // 4. Guardar Observaciones (CORREGIDO: setUsuario -> setUsername)
        if (in.getObservaciones() != null && !in.getObservaciones().isEmpty()) {
            List<ObservacionesSegreEntity> obs = in.getObservaciones().stream()
                .map(txt -> {
                    ObservacionesSegreEntity o = new ObservacionesSegreEntity();
                    o.setIdSolicitud(idSolicitud);
                    
                    // CORRECCIÓN AQUÍ: Tu entidad ObservacionesSegreEntity usa 'username'
                    o.setUsername(usuarioSolicitante); 
                    
                    o.setObservacion(txt);
                    // El @PrePersist pone la fecha, o la pones manual:
                    o.setFechaObservacion(LocalDateTime.now());
                    return o;
                })
                .collect(Collectors.toList());
            observacionRepo.saveAll(obs);
        }

        // 5. Iniciar Instancia de Proceso (BPM Simulado)
        InstanciaProcesoEntity inst = new InstanciaProcesoEntity();
        inst.setIdInstancia(UUID.randomUUID());
        inst.setIdSolicitud(idSolicitud);
        inst.setUsuarioCreador(usuarioSolicitante);
        inst.setEstado("EN_PROCESO");
        inst.setFechaInicio(LocalDateTime.now());
        inst = instanciaRepo.save(inst);

        // Crear las tareas para la Huella de Auditoría
        crearTarea(inst.getIdInstancia(), "APROBAR_RECHAZAR", 1, "EN_PROCESO");
        crearTarea(inst.getIdInstancia(), "ASIGNAR_ACCESOS", 2, "PENDIENTE");
        crearTarea(inst.getIdInstancia(), "CONTROL_TIEMPO", 3, "PENDIENTE");
        crearTarea(inst.getIdInstancia(), "QUITAR_ACCESOS", 4, "PENDIENTE");

        return loadSolicitudDto(idSolicitud);
    }

    private void crearTarea(UUID idInstancia, String nombre, int orden, String estadoInicial) {
        TareasProcesoEntity t = new TareasProcesoEntity();
        t.setIdTarea(UUID.randomUUID());
        t.setIdInstancia(idInstancia);
        t.setNombreTarea(nombre);
        t.setOrdenEjecucion(orden);
        t.setEstado(estadoInicial);
        if ("EN_PROCESO".equals(estadoInicial)) {
            t.setFechaInicio(LocalDateTime.now());
        }
        tareasRepo.save(t);
    }

    /* ============ AGREGAR OBJETO (POST-CREACIÓN) ============ */
    @Transactional
    public SolicitudObjetoDetalleDto agregarObjeto(UUID idSolicitud, SolicitudObjetoDetalleDto d) {
        validarExisteSolicitud(idSolicitud);
        SolicitudObjetoDetalleEntity e = SolicitudSeguridadMapper.toEntity(idSolicitud, d);
        e = objetoRepo.save(e);
        return SolicitudSeguridadMapper.toDto(e);
    }

    /* ============ AGREGAR USUARIO (POST-CREACIÓN) ============ */
    @Transactional
    public SolicitudUsuarioDetalleDto agregarUsuario(UUID idSolicitud, String username, String usuarioAutorizador) {
        validarExisteSolicitud(idSolicitud);
        
        SolicitudUsuarioDetalleEntity e = new SolicitudUsuarioDetalleEntity();
        e.setIdSolicitud(idSolicitud);
        e.setUsuario(username); // Aquí si es setUsuario según tu otra entidad
        e.setUsuarioAutorizador(usuarioAutorizador);
        e.setFechaAutorizacion(LocalDateTime.now());
        e.setAutorizado(true); 
        
        e = usuarioDetRepo.save(e);
        return SolicitudSeguridadMapper.toDto(e);
    }

    /* ============ AGREGAR OBSERVACIÓN (POST-CREACIÓN) ============ */
    @Transactional
    public ObservacionDto agregarObservacion(UUID idSolicitud, String username, String obs) {
        validarExisteSolicitud(idSolicitud);
        
        ObservacionesSegreEntity e = new ObservacionesSegreEntity();
        e.setIdSolicitud(idSolicitud);
        e.setUsername(username); // CORREGIDO: setUsername
        e.setObservacion(obs);
        e.setFechaObservacion(LocalDateTime.now());
        
        e = observacionRepo.save(e);
        return SolicitudSeguridadMapper.toDto(e);
    }

    /* ============ APROBAR / RECHAZAR ============ */
    @Transactional
    public SolicitudSeguridadDto aprobar(UUID idSolicitud, String responsable, String comentario) {
        SolicitudSeguridadEntity s = validarExisteSolicitud(idSolicitud);
        s.setEstado("APROBADA");
        solicitudRepo.save(s);
        agregarObservacion(idSolicitud, responsable, "APROBADA: " + Optional.ofNullable(comentario).orElse(""));

        cerrarTarea(idSolicitud, "APROBAR_RECHAZAR", responsable, true);
        return loadSolicitudDto(idSolicitud);
    }

    @Transactional
    public SolicitudSeguridadDto rechazar(UUID idSolicitud, String responsable, String comentario) {
        SolicitudSeguridadEntity s = validarExisteSolicitud(idSolicitud);
        s.setEstado("RECHAZADA");
        solicitudRepo.save(s);
        agregarObservacion(idSolicitud, responsable, "RECHAZADA: " + Optional.ofNullable(comentario).orElse(""));

        cerrarTarea(idSolicitud, "APROBAR_RECHAZAR", responsable, true);
        cerrarInstancia(idSolicitud, "RECHAZADA");
        return loadSolicitudDto(idSolicitud);
    }

    /* ============ ASIGNAR / QUITAR ============ */
    @Transactional
    public SolicitudSeguridadDto asignarAccesos(UUID idSolicitud, String responsable) {
        SolicitudSeguridadEntity s = validarExisteSolicitud(idSolicitud);
        // Validaciones...
        agregarObservacion(idSolicitud, responsable, "ASIGNACIÓN realizada (simulada).");
        cerrarTarea(idSolicitud, "ASIGNAR_ACCESOS", responsable, true);

        s.setEstado("ASIGNADA");
        solicitudRepo.save(s);
        return loadSolicitudDto(idSolicitud);
    }

    @Transactional
    public SolicitudSeguridadDto quitarAccesos(UUID idSolicitud, String responsable) {
        SolicitudSeguridadEntity s = validarExisteSolicitud(idSolicitud);
        agregarObservacion(idSolicitud, responsable, "REVOCACIÓN de accesos (simulada).");
        cerrarTarea(idSolicitud, "QUITAR_ACCESOS", responsable, true);

        s.setEstado("CERRADA");
        solicitudRepo.save(s);
        cerrarInstancia(idSolicitud, "CERRADA");
        return loadSolicitudDto(idSolicitud);
    }
    
    @Transactional
    public List<SolicitudSeguridadDto> revocarSolicitudesVencidas(String responsableSistema) {
         List<SolicitudSeguridadEntity> vencidas = solicitudRepo.findByAccesoHastaBefore(LocalDateTime.now());
         // OJO: Tu lógica original buscaba por fecha cabecera, ahora la fecha está en los usuarios. 
         // Para mantener compatibilidad simple, dejamos esto, pero lo ideal es buscar usuarios vencidos.
         List<SolicitudSeguridadDto> out = new ArrayList<>();
         // ... (implementación según requieras)
         return out;
    }

    /* ============ CONSULTA ============ */
    @Transactional(readOnly = true)
    public SolicitudSeguridadDto loadSolicitudDto(UUID idSolicitud) {
        SolicitudSeguridadEntity s = solicitudRepo.findById(idSolicitud)
                .orElseThrow(() -> new NoSuchElementException("Solicitud no encontrada"));

        List<SolicitudObjetoDetalleEntity> objs = objetoRepo.findByIdSolicitud(idSolicitud);
        List<SolicitudUsuarioDetalleEntity> usrs = usuarioDetRepo.findByIdSolicitud(idSolicitud);
        List<ObservacionesSegreEntity> obs = observacionRepo.findByIdSolicitudOrderByFechaObservacionAsc(idSolicitud);

        return SolicitudSeguridadMapper.toDto(s, objs, usrs, obs);
    }

    /* ============ HELPERS ============ */
    private SolicitudSeguridadEntity validarExisteSolicitud(UUID idSolicitud) {
        return solicitudRepo.findById(idSolicitud)
                .orElseThrow(() -> new NoSuchElementException("Solicitud no encontrada"));
    }

    private void cerrarTarea(UUID idSolicitud, String nombreTarea, String responsable, boolean finalizar) {
        List<InstanciaProcesoEntity> insts = instanciaRepo.findByIdSolicitud(idSolicitud);
        if (insts.isEmpty()) return;
        UUID idInst = insts.get(0).getIdInstancia();
        List<TareasProcesoEntity> tasks = tareasRepo.findByIdInstanciaOrderByOrdenEjecucionAsc(idInst)
                .stream().filter(t -> t.getNombreTarea().equalsIgnoreCase(nombreTarea)).collect(Collectors.toList());
        if (tasks.isEmpty()) return;
        TareasProcesoEntity t = tasks.get(0);
        t.setResponsable(responsable);
        t.setFechaFin(LocalDateTime.now());
        t.setEstado(finalizar ? "FINALIZADA" : "EN_PROCESO");
        tareasRepo.save(t);
    }

    private void cerrarInstancia(UUID idSolicitud, String estado) {
        List<InstanciaProcesoEntity> insts = instanciaRepo.findByIdSolicitud(idSolicitud);
        if (insts.isEmpty()) return;
        InstanciaProcesoEntity i = insts.get(0);
        i.setEstado(estado);
        i.setFechaFin(LocalDateTime.now());
        instanciaRepo.save(i);
    }
}