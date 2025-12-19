package com.soaint.gestion.areas.api.entity;

import java.util.UUID;

import javax.persistence.*;

import lombok.Data;
@Data
@Entity
@Table(name = "TiposDocumentales", schema = "dbo")
public class TiposDocumentalesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "TipoDocumental")
    private String tipoDocumental;

    @Column(name = "NombreDocumento")
    private String nombreDocumento;

    @Column(name = "CodTIpoDocumental")
    private String codTipoDocumental;

    @Column(name = "Etiqueta")
    private String etiqueta;

    @Column(name = "NombreOficinaProductora")
    private String nombreOficinaProductora;

    @Column(name = "SerieDocumental")
    private String serieDocumental;

    @Column(name = "SubSerieDocumental")
    private String subSerieDocumental;

    @Column(name = "NivelAcceso")
    private String nivelAcceso;
    
    @Column(name = "SeguridadSegregada")
    private Integer seguridadSegregada;

}
