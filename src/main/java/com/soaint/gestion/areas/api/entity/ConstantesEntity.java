package com.soaint.gestion.areas.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Constantes", schema = "dbo")
public class ConstantesEntity {
    @Id @Column(name="Id")      private Integer id;
    @Column(name="ParentId")    private String  parentId;
    @Column(name="Codigo")      private String  codigo;
    @Column(name="Nombre")      private String  nombre;
    @Column(name="Activo")      private Integer activo;
}