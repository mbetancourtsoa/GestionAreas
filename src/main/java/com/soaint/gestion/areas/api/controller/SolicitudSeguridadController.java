package com.soaint.gestion.areas.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soaint.gestion.areas.api.dto.ObservacionDto;
import com.soaint.gestion.areas.api.dto.SimpleApiResponse;
import com.soaint.gestion.areas.api.dto.SolicitudObjetoDetalleDto;
import com.soaint.gestion.areas.api.dto.SolicitudSeguridadCreateDto;
import com.soaint.gestion.areas.api.dto.SolicitudSeguridadDto;
import com.soaint.gestion.areas.api.dto.SolicitudUsuarioDetalleDto;
import com.soaint.gestion.areas.api.service.ConstantesService;
import com.soaint.gestion.areas.api.service.SeguridadSegregadaService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.var;


@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200")   // <-- GARANTIZA QUE FUNCIONE
@RequiredArgsConstructor
public class SolicitudSeguridadController {

    private final SeguridadSegregadaService service;
    private final ConstantesService constantesSvc;

    /* Crear solicitud */
    @PostMapping
    public ResponseEntity<SimpleApiResponse<SolicitudSeguridadDto>> crear(
            @RequestBody SolicitudSeguridadCreateDto in,
            @RequestParam String usuarioSolicitante) {

        SolicitudSeguridadDto body = service.crearSolicitud(in, usuarioSolicitante);
        return ResponseEntity.ok(
                SimpleApiResponse.<SolicitudSeguridadDto>builder()
                        .body(body).businessStatus("200")
                        .timeResponse(constantesSvc.nowString())
                        .message("Solicitud creada.")
                        .build()
        );
    }

    /* Obtener por id */
    @GetMapping("/{id}")
    public ResponseEntity<SimpleApiResponse<SolicitudSeguridadDto>> get(@PathVariable UUID id) {
        SolicitudSeguridadDto body = service.loadSolicitudDto(id);
        return ResponseEntity.ok(
                SimpleApiResponse.<SolicitudSeguridadDto>builder()
                        .body(body).businessStatus("200")
                        .timeResponse(constantesSvc.nowString())
                        .message("Solicitud consultada.")
                        .build()
        );
    }

    /* Agregar objeto */
    @PostMapping("/{id}/objetos")
    public ResponseEntity<SimpleApiResponse<SolicitudObjetoDetalleDto>> addObjeto(
            @PathVariable UUID id, @RequestBody SolicitudObjetoDetalleDto d) {
        var body = service.agregarObjeto(id, d);
        return ResponseEntity.ok(SimpleApiResponse.<SolicitudObjetoDetalleDto>builder()
                .body(body).businessStatus("200")
                .timeResponse(constantesSvc.nowString())
                .message("Objeto agregado.")
                .build());
    }

    /* Agregar usuario */
    @PostMapping("/{id}/usuarios")
    public ResponseEntity<SimpleApiResponse<SolicitudUsuarioDetalleDto>> addUsuario(
            @PathVariable UUID id, @RequestParam String username, @RequestParam String usuarioAutorizador) {
        var body = service.agregarUsuario(id, username, usuarioAutorizador);
        return ResponseEntity.ok(SimpleApiResponse.<SolicitudUsuarioDetalleDto>builder()
                .body(body).businessStatus("200")
                .timeResponse(constantesSvc.nowString())
                .message("Usuario agregado.")
                .build());
    }

    /* Agregar observación */
    @PostMapping("/{id}/observaciones")
    public ResponseEntity<SimpleApiResponse<ObservacionDto>> addObs(
            @PathVariable UUID id, @RequestParam String username, @RequestParam String observacion) {
        var body = service.agregarObservacion(id, username, observacion);
        return ResponseEntity.ok(SimpleApiResponse.<ObservacionDto>builder()
                .body(body).businessStatus("200")
                .timeResponse(constantesSvc.nowString())
                .message("Observación agregada.")
                .build());
    }

    /* Aprobar / Rechazar */
    @PostMapping("/{id}/aprobar")
    public ResponseEntity<SimpleApiResponse<SolicitudSeguridadDto>> aprobar(
            @PathVariable UUID id, @RequestParam String responsable, @RequestParam(required = false) String comentario) {
        var body = service.aprobar(id, responsable, comentario);
        return ResponseEntity.ok(SimpleApiResponse.<SolicitudSeguridadDto>builder()
                .body(body).businessStatus("200")
                .timeResponse(constantesSvc.nowString())
                .message("Solicitud aprobada.")
                .build());
    }

    @PostMapping("/{id}/rechazar")
    public ResponseEntity<SimpleApiResponse<SolicitudSeguridadDto>> rechazar(
            @PathVariable UUID id, @RequestParam String responsable, @RequestParam(required = false) String comentario) {
        var body = service.rechazar(id, responsable, comentario);
        return ResponseEntity.ok(SimpleApiResponse.<SolicitudSeguridadDto>builder()
                .body(body).businessStatus("200")
                .timeResponse(constantesSvc.nowString())
                .message("Solicitud rechazada.")
                .build());
    }

    /* Asignar y Quitar accesos */
    @PostMapping("/{id}/asignar")
    public ResponseEntity<SimpleApiResponse<SolicitudSeguridadDto>> asignar(
            @PathVariable UUID id, @RequestParam String responsable) {
        var body = service.asignarAccesos(id, responsable);
        return ResponseEntity.ok(SimpleApiResponse.<SolicitudSeguridadDto>builder()
                .body(body).businessStatus("200")
                .timeResponse(constantesSvc.nowString())
                .message("Accesos asignados.")
                .build());
    }

    @PostMapping("/{id}/quitar")
    public ResponseEntity<SimpleApiResponse<SolicitudSeguridadDto>> quitar(
            @PathVariable UUID id, @RequestParam String responsable) {
        var body = service.quitarAccesos(id, responsable);
        return ResponseEntity.ok(SimpleApiResponse.<SolicitudSeguridadDto>builder()
                .body(body).businessStatus("200")
                .timeResponse(constantesSvc.nowString())
                .message("Accesos quitados.")
                .build());
    }

    /* Revocar vencidas (control tiempo) */
    @GetMapping("/vencidas/revocar")
    public ResponseEntity<SimpleApiResponse<List<SolicitudSeguridadDto>>> revocarVencidas(
            @RequestParam(defaultValue = "sistema") String responsableSistema) {
        var body = service.revocarSolicitudesVencidas(responsableSistema);
        return ResponseEntity.ok(SimpleApiResponse.<List<SolicitudSeguridadDto>>builder()
                .body(body).businessStatus("200")
                .timeResponse(constantesSvc.nowString())
                .message("Solicitudes vencidas procesadas.")
                .build());
    }
}
