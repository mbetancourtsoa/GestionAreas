package com.soaint.gestion.areas.api.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class SolicitudUsuarioDetalleDto {
	private Long id;
    private UUID idUsuario; 
    private String username;
    private String nombre;  
    private LocalDateTime fechaHasta; 
    private Boolean autorizado;
    private LocalDateTime fechaAutorizacion;
    private String usuarioAutorizador;
}
