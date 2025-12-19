package com.soaint.gestion.areas.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ObservacionDto {
    private Long id;
    private String username;
    private String observacion;
    private LocalDateTime fechaObservacion;
}
