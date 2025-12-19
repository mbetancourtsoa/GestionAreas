package com.soaint.gestion.areas.api.dto;

import lombok.Data;

@Data
public class DeclaracionRequest {

    private String idDocumento;      
    private String nombreDocumento;  
    private String rutaExpediente;   
    private String objectStore;  
    private String metodoTraslado;
}