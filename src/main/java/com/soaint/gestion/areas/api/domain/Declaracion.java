package com.soaint.gestion.areas.api.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@Table(name = "Declaracion", schema = "dbo", catalog = "ColpensionesBusinessCoreINT")
@Table(
		  name = "Declaracion",
		  schema = "dbo",
		  catalog = "GD_NEGOCIO_AREAS"
		)
@Data @NoArgsConstructor
public class Declaracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdDeclaracion")
    private Integer id;

    @Column(name = "IdDocumento", nullable = false, length = 100)
    private String idDocumento;

    @Column(name = "NombreDocumento", nullable = false, length = 255)
    private String nombreDocumento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EstadoId", nullable = false)
    private EstadoDeclaracion estado;

    @Column(name = "FechaCreacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "FechaEjecucion")
    private LocalDateTime fechaEjecucion;

    @Column(name = "RutaExpediente", length = 500)
    private String rutaExpediente;

    @Column(name = "ObjectStore", length = 100)
    private String objectStore;
    
    @Column(name= "TipoTraslado", length=100)
    private String tipoTraslado;
}
