package com.soaint.gestion.areas.api.dto;

import lombok.Data;

@Data
public class SolicitudObjetoDetalleDto {
    private Long id;                     // null al crear
    private String tipoObjeto;           // EXPEDIENTE|DOCUMENTO
    private String identificadorObjeto;  // folderName/documentId
    private String nombreObjeto;
    private String clase;
    private String sensibilidad;
    private String observacion;
}
