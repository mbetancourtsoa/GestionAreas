package com.soaint.gestion.areas.api.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.soaint.gestion.areas.api.dto.CatalogoCategoriaDTO;
import com.soaint.gestion.areas.api.dto.OficinaProductoraDTO;
import com.soaint.gestion.areas.api.dto.TipoDocumentalDto;
import com.soaint.gestion.areas.api.entity.CategoriasEntity;
import com.soaint.gestion.areas.api.entity.RuleConfigEntity;
import com.soaint.gestion.areas.api.entity.TiposDocumentalesEntity;
import com.soaint.gestion.areas.api.repository.CatalogoAreasRepository;
import com.soaint.gestion.areas.api.repository.CategoriasRepository;
import com.soaint.gestion.areas.api.repository.RuleConfigRepository;
import com.soaint.gestion.areas.api.repository.TiposDocumentalesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CatalogoServiceImpl implements CatalogoService {

    private final CatalogoAreasRepository areasRepo;
    private final CategoriasRepository categoriasRepo;
    private final RuleConfigRepository ruleConfigRepo;
    private final TiposDocumentalesRepository tipoDocRepo;    
    @Override
    public List<OficinaProductoraDTO> listarOficinas() {
        return areasRepo.findAllOrdenadas()
                .stream()
                .map(a -> {
                    OficinaProductoraDTO dto = new OficinaProductoraDTO();
                    dto.setIdArea(a.getId());
                    dto.setNombreOficinaProductora(a.getNombreOficinaProductora());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<CatalogoCategoriaDTO> obtenerCategoriasPorArea(UUID idArea) {

        List<CategoriasEntity> categorias = categoriasRepo.findByIdArea(idArea);

        return categorias.stream().map(cat -> {

            RuleConfigEntity rc = ruleConfigRepo
                    .findByIdAreaAndIdCategoria(idArea, cat.getIdCategoria())
                    .orElse(null);

            CatalogoCategoriaDTO dto = new CatalogoCategoriaDTO();
            dto.setIdCategoria(cat.getIdCategoria());
            dto.setNombreCategoria(cat.getNombreCategoria());
            dto.setClaseDocumento(cat.getClaseDocumento());
            dto.setClaseExpediente(cat.getClaseExpediente());
            dto.setCarpetaAgrupadora(rc != null ? rc.getCarpetaAgrupadora() : null);

            return dto;
        }).collect(Collectors.toList());
    }
    
    @Override // <--- Implementación del método de la interfaz
    public List<TipoDocumentalDto> listarTiposDocumentalesSegregados() {
        // Buscamos los que tienen valor 1 (suponiendo que 1 es activo)
        List<TiposDocumentalesEntity> entities = tipoDocRepo.findBySeguridadSegregada(1);
        
        return entities.stream().map(e -> {
            TipoDocumentalDto dto = new TipoDocumentalDto();
            dto.setId(e.getId());
            dto.setNombreDocumento(e.getNombreDocumento());
            dto.setCodTipoDocumental(e.getCodTipoDocumental());
            return dto;
        }).collect(Collectors.toList());
    }
    
    
}