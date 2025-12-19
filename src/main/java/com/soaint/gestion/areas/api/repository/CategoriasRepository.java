package com.soaint.gestion.areas.api.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.soaint.gestion.areas.api.entity.CategoriasEntity;

@Repository
public interface CategoriasRepository extends JpaRepository<CategoriasEntity, UUID> {

    // Buscar categoría por nombre exacto
    Optional<CategoriasEntity> findByNombreCategoria(String nombreCategoria);
    List<CategoriasEntity> findByIdArea(UUID idArea);

    // Validar existencia por idCategoria (UUID)
    boolean existsByIdCategoria(UUID idCategoria);
    
    @Query("SELECT c FROM CategoriasEntity c WHERE TRIM(UPPER(c.nombreCategoria)) = TRIM(UPPER(:nombreCategoria))")
    Optional<CategoriasEntity> findByNombreCategoriaLimpio(@Param("nombreCategoria") String nombreCategoria);
    
    
    @Query(
            "SELECT c " +
            "FROM   CategoriasEntity c " +
            "WHERE  TRIM(UPPER(c.nombreCategoria)) = TRIM(UPPER(:nombre)) " +
            "  AND  c.idArea = :idArea"
        )
        Optional<CategoriasEntity> findByNombreAndArea(@Param("nombre") String nombre,
                                                       @Param("idArea") UUID idArea);
    
    /* LIKE por nombre (case-insensitive) */
    Page<CategoriasEntity> findByNombreCategoriaContainingIgnoreCase(
            String nombre, Pageable pg);

    /* Filtra por área + like en nombre */
    @Query("SELECT c " +
           "FROM   CategoriasEntity c " +
           "WHERE  c.idArea = :idArea " +
           "AND   (:nombre IS NULL OR " +
           "       UPPER(c.nombreCategoria) LIKE CONCAT('%', UPPER(:nombre), '%'))")
    Page<CategoriasEntity> findByAreaAndNombreLike(
            @Param("idArea") UUID idArea,
            @Param("nombre")  String nombre,
            Pageable pg);    
    
    
    
    boolean existsByNombreCategoriaIgnoreCaseAndIdArea(String nombre, UUID idArea);
    boolean existsByNombreCategoriaIgnoreCaseAndIdAreaAndIdCategoriaNot(
            String nombre, UUID idArea, UUID idCategoria);

}
