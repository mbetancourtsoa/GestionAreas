package com.soaint.gestion.areas.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data                
@NoArgsConstructor  
@AllArgsConstructor 
public class TipoDocumentalDto {
    private Integer id;
    private String  tipoDocumental;          
    private String  nombreDocumento;         
    private String  codTipoDocumental;       
    private String  etiqueta;                
    private String  nombreOficinaProductora; 
    private String  serieDocumental;         
    private String  subSerieDocumental;      
    private String  nivelAcceso;             
}