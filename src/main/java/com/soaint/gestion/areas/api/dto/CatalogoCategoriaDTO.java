package com.soaint.gestion.areas.api.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class CatalogoCategoriaDTO {
    private UUID idCategoria;
    private String nombreCategoria;
    private String claseExpediente;
    private String claseDocumento;
    private String carpetaAgrupadora;
}