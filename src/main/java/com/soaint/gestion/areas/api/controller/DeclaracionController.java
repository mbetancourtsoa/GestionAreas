package com.soaint.gestion.areas.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.soaint.gestion.areas.api.domain.Declaracion;
import com.soaint.gestion.areas.api.dto.DeclaracionRequest;
import com.soaint.gestion.areas.api.dto.DeclaracionResponse;
import com.soaint.gestion.areas.api.dto.ErrorDeclaracionResponse;
import com.soaint.gestion.areas.api.repository.ErrorDeclaracionRepository;
import com.soaint.gestion.areas.api.service.DeclaracionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;


@RestController
@RequestMapping("/api/declaraciones")
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
public class DeclaracionController {

    private final DeclaracionService service;

    private final ErrorDeclaracionRepository errorDeclaracionRepo;
    

    
    @GetMapping("/{id}/errores")
    public List<ErrorDeclaracionResponse> listarErrores(@PathVariable Integer id) {
        return errorDeclaracionRepo                      // 🔸 usa el nuevo método
                 .findByDeclaracion_Id(id)
                 .stream()
                 .map(ErrorDeclaracionResponse::from)
                 .collect(Collectors.toList());
    }
    
    @PostMapping
    public ResponseEntity<DeclaracionResponse> registrar(@RequestBody DeclaracionRequest req) {
        Declaracion d = service.registrarEventoDocumento(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(DeclaracionResponse.from(d));
    }


    
    @PostMapping("/{id}/retry")
    public void reintentar(@PathVariable Integer id) { service.reintentar(id); }
    
    
    @GetMapping(path = "/errores", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<DeclaracionResponse>> listarErrores(
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fechaCreacion,desc") String sort) {

        String[] s = sort.split(",");
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.Direction.fromString(s.length > 1 ? s[1] : "asc"),
                s[0]);

        Page<DeclaracionResponse> result = service
                .obtenerErrores(pageable)
                .map(DeclaracionResponse::from);

        return ResponseEntity.ok(result);
    }
    
    
    @PostMapping("/declarar")
    public ResponseEntity<Void> declarar(@RequestBody List<Integer> ids) {
    	System.out.println("Clase desde declarar");
        service.declararSeleccionados(ids);
        return ResponseEntity.ok().build();
    }
}
