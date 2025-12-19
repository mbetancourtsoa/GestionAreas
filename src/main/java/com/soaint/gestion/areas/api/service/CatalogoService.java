package com.soaint.gestion.areas.api.service;

import java.util.List;
import java.util.UUID;

import com.soaint.gestion.areas.api.dto.CatalogoCategoriaDTO;
import com.soaint.gestion.areas.api.dto.OficinaProductoraDTO;
import com.soaint.gestion.areas.api.dto.TipoDocumentalDto;

public interface CatalogoService {

	  List<OficinaProductoraDTO> listarOficinas();
	  List<CatalogoCategoriaDTO> obtenerCategoriasPorArea(UUID idArea);
	  List<TipoDocumentalDto> listarTiposDocumentalesSegregados();
}
