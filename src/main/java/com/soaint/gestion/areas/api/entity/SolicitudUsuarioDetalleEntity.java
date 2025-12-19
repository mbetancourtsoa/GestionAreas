package com.soaint.gestion.areas.api.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "SolicitudUsuarioDetalle", schema = "dbo")
public class SolicitudUsuarioDetalleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "IdSolicitud")
    private UUID idSolicitud;

 // EN LUGAR DE @Column(name = "Usuario"...)
    @Column(name = "Usuario", length = 150) 
    private String usuario;                  

    // NUEVO CAMPO: Para guardar el nombre completo (ej: "Oliver Rivera")
    @Column(name = "NombreUsuario", length = 255)
    private String nombreUsuario; 

    @Column(name = "Autorizado")
    private Boolean autorizado;

    @Column(name = "FechaAutorizacion")
    private LocalDateTime fechaAutorizacion;

    @Column(name = "UsuarioAutorizador", length = 150)
    private String usuarioAutorizador;

    // NUEVO CAMPO: Para la regla de negocio "Acceso Hasta" por usuario
    @Column(name = "FechaHasta")
    private LocalDateTime fechaHasta;
}