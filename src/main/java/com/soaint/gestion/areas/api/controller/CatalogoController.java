package com.soaint.gestion.areas.api.controller;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soaint.gestion.areas.api.dto.AreaUsuarioDto;
import com.soaint.gestion.areas.api.dto.CatalogoCategoriaDTO;
import com.soaint.gestion.areas.api.dto.GenericResponseDTO;
import com.soaint.gestion.areas.api.dto.OficinaProductoraDTO;
import com.soaint.gestion.areas.api.dto.SimpleApiResponse;
import com.soaint.gestion.areas.api.dto.TipoDocumentalDto;
import com.soaint.gestion.areas.api.service.CatalogoService;
import com.soaint.gestion.areas.api.service.ConstantesService;
import com.soaint.gestion.areas.api.service.UserAdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/catalogo")
@RequiredArgsConstructor
public class CatalogoController {

    private final CatalogoService catalogoService;
    private final	 UserAdminService  userAdminService;
    private final ConstantesService constantesSvc;

    @GetMapping("/{username}/area")
    public ResponseEntity<SimpleApiResponse<AreaUsuarioDto>> obtenerAreaUsuario(
            @PathVariable String username) {

        AreaUsuarioDto dto = userAdminService.obtenerAreaUsuario(username);

        SimpleApiResponse<AreaUsuarioDto> resp = SimpleApiResponse.<AreaUsuarioDto>builder()
                .body(dto)
                .businessStatus("200")
                .timeResponse(constantesSvc.nowString())
                .message("Área consultada correctamente.")
                .build();

        return ResponseEntity.ok(resp);
    }
    @GetMapping("/oficinas")
    public ResponseEntity<GenericResponseDTO<List<OficinaProductoraDTO>>> listarOficinas() {

        List<OficinaProductoraDTO> body = catalogoService.listarOficinas();

        GenericResponseDTO<List<OficinaProductoraDTO>> response =
                GenericResponseDTO.<List<OficinaProductoraDTO>>builder()
                        .data(body)
                        .build();

        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/categorias")
    public ResponseEntity<GenericResponseDTO<List<CatalogoCategoriaDTO>>> listarCategorias(
            @RequestParam UUID idArea) {

        List<CatalogoCategoriaDTO> body = catalogoService.obtenerCategoriasPorArea(idArea);

        GenericResponseDTO<List<CatalogoCategoriaDTO>> response =
                GenericResponseDTO.<List<CatalogoCategoriaDTO>>builder()
                        .data(body)
                        .build();

        return ResponseEntity.ok(response);
    }
    
    
    @GetMapping("/tipos-documentales/segregados")
    public ResponseEntity<SimpleApiResponse<List<TipoDocumentalDto>>> getTiposDocumentales() {
        
        // CORRECCIÓN AQUÍ: catalogoService (sin 's')
        List<TipoDocumentalDto> list = catalogoService.listarTiposDocumentalesSegregados(); 
        
        return ResponseEntity.ok(SimpleApiResponse.<List<TipoDocumentalDto>>builder()
                .body(list)
                .message("Tipos documentales consultados")
                .businessStatus("200")
                .build());
    }

}
