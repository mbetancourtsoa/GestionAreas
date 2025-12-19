package com.soaint.gestion.areas.api.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.soaint.gestion.areas.api.entity.RuleConfigEntity;

@Repository
public interface RuleConfigRepository extends JpaRepository<RuleConfigEntity, Long> {

	 @Query("SELECT r FROM RuleConfigEntity r " +
	           "WHERE r.idArea = :idArea AND r.idCategoria = :idCategoria")
	    Optional<RuleConfigEntity> findByIdAreaAndIdCategoria(@Param("idArea") UUID idArea,
	                                                          @Param("idCategoria") UUID idCategoria);

	    boolean existsByIdArea(UUID idArea);

	    /* ---------- NUEVOS MÉTODOS ---------- */

	    /* comprobar duplicado exacto (área + categoría) */
	    boolean existsByIdAreaAndIdCategoria(UUID idArea, UUID idCategoria);

	    /* duplicado excluyendo la propia regla (para update) */
	    boolean existsByIdAreaAndIdCategoriaAndIdNot(UUID idArea,
	                                                 UUID idCategoria,
	                                                 Long id);

	    /* paginación con filtros dinámicos (área y categoría opcionales) */
	    @Query("SELECT r FROM RuleConfigEntity r " +
	           "WHERE (:idArea IS NULL OR r.idArea = :idArea) " +
	           "  AND (:idCat  IS NULL OR r.idCategoria = :idCat)")
	    Page<RuleConfigEntity> findByFiltros(@Param("idArea") UUID idArea,
	                                         @Param("idCat")  UUID idCategoria,
	                                         Pageable pg);
}
