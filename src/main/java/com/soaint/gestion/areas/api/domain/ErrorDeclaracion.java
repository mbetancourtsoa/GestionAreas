package com.soaint.gestion.areas.api.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor          
@Entity
//@Table(name = "ErrorDeclaracion", schema = "dbo",       catalog = "ColpensionesBusinessCoreINT")
@Table(name = "ErrorDeclaracion")

@Data @NoArgsConstructor @Builder               // si usas builder
public class ErrorDeclaracion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdError")
    private Integer id;

    /* FK correcta → IdDeclaracion */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdDeclaracion",
                nullable = false,
                foreignKey = @ForeignKey(name = "FK_ErrorDeclaracion_Declaracion"))
    private Declaracion declaracion;

    @Column(name = "TipoError", length = 50)
    private String tipoError;

    @Column(name = "MensajeError", length = 2000)
    private String mensajeError;

    @Column(name = "TraceId", length = 100)
    private String traceId;

    @Column(name = "FechaError", nullable = false)
    private LocalDateTime fechaError;
}