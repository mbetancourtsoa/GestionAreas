package com.soaint.gestion.areas.api.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Usuarios", schema = "dbo")
public class UsuariosEntity {

    @Id
    @Column(name = "IdUsuario")
    private UUID id;

    @Column(name = "Username")
    private String username;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Email")
    private String email;

    @Column(name = "Activo")
    private Boolean activo;
}
