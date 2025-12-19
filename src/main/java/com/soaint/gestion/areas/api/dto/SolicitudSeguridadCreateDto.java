package com.soaint.gestion.areas.api.dto;

import lombok.Data;
import java.util.List;

@Data
public class SolicitudSeguridadCreateDto {
    private String areaSolicitante;
    private String tipoSolicitud;
    private String oficinaProductora; // ID del área
    private String serieSubserie;     // ID de la categoría
    private String nombreExpediente;
    private String accesoHasta;       // (Opcional si la fecha es por usuario)
    
    // CORRECCIÓN: Usamos una lista de objetos, no de strings, ni de Objects genéricos
    private List<SolicitudUsuarioCreateDto> usuarios; 
    
    private List<String> observaciones;
}