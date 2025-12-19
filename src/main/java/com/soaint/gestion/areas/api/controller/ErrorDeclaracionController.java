package com.soaint.gestion.areas.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.soaint.gestion.areas.api.dto.ErrorDeclaracionDto;
import com.soaint.gestion.areas.api.dto.ErrorDeclaracionRequest;
import com.soaint.gestion.areas.api.service.ErrorDeclaracionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/api/errores")
@CrossOrigin(
	    origins = "*",
	    methods = {
	      RequestMethod.GET,
	      RequestMethod.POST,
	      RequestMethod.PUT,
	      RequestMethod.DELETE
	    }
	)
@Log4j
@RequiredArgsConstructor
public class ErrorDeclaracionController {

    private final ErrorDeclaracionService service;

    /* ---------------------------------------------------- 1) Listar */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ErrorDeclaracionDto> listar(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime desde,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime hasta) {

        return service.listarPorRango(desde, hasta);
    }

    /* ---------------------------------------------------- 2) Reintentar */
    @PostMapping("/{id}/retry")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reintentar(@PathVariable Integer id) {
        service.reintentarError(id);
    }

    /* ---------------------------------------------------- 3) Crear (opcional) */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ErrorDeclaracionDto> crearError(
            @RequestBody ErrorDeclaracionRequest request) {

        ErrorDeclaracionDto dto = service.crearError(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}