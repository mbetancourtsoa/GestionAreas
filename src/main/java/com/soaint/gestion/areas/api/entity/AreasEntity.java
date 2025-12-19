package com.soaint.gestion.areas.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "Areas", schema = "dbo")
public class AreasEntity {

	@Id
	@Column(name = "IdArea")
	private UUID id;

    @Column(name = "Sigla")
    private String sigla;

    @Column(name = "ObjectStore")
    private String objectStore;

    @Column(name = "NombreOficinaProductora")
    private String nombreOficinaProductora;
    

    @Column(name = "Etiqueta")
    private String etiqueta;

    @Column(name = "FileSystemName")
    private String fileSystemName;

    @Column(name = "CodigoOficinaProductora")
    private String codOficinaProductora;
}
