package com.soaint.gestion.areas.api.dto;

import lombok.Data;
import java.util.Date; // O String si prefieres parsear manual, pero Date/LocalDateTime es mejor

@Data
public class SolicitudUsuarioCreateDto {
    private String usuario;       // samAccountName
    private String nombre;        // Nombre completo (para no perderlo si el LDAP falla luego)
    private Date fechaHasta;      // La fecha límite de acceso
}