package com.soaint.gestion.areas.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.Builder;
import lombok.Data;

@Data @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponseDTO<T> {
    @JsonUnwrapped
    private T data;       // payload en caso de éxito
    @JsonUnwrapped
    private ErrorDTO error;  // presente sólo cuando hay fallo
}