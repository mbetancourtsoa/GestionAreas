package com.soaint.gestion.areas.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "RuleConfig", schema = "dbo")
public class RuleConfigEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "IdArea")
    private UUID idArea;

    @Column(name = "IdCategoria")
    private UUID idCategoria;

    @Column(name = "ReglaAgrupadoraExpediente")
    private String reglaAgrupadoraExpediente;

    @Column(name = "MetadataEspecifica")
    private Integer metadataEspecifica;

    @Column(name = "AplicaMetadataExpediente")
    private Integer aplicaMetadataExpediente;

    @Column(name = "PosicionMetadata")
    private String posicionMetadata;

    @Column(name = "MetadatoExp1")
    private String metadatoExp1;

    @Column(name = "MetadatoExp2")
    private String metadatoExp2;

    @Column(name = "MetadatoExp3")
    private String metadatoExp3;

    @Column(name = "MetadatoExp4")
    private String metadatoExp4;

    @Column(name = "MetadatoDoc1")
    private String metadatoDoc1;

    @Column(name = "MetadatoDoc2")
    private String metadatoDoc2;

    @Column(name = "MetadatoDoc3")
    private String metadatoDoc3;

    @Column(name = "MetadatoDoc4")
    private String metadatoDoc4;
    
    @Column(name = "CarpetaAgrupadora")
    private String carpetaAgrupadora;
}
