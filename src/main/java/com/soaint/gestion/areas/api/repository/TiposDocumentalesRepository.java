package com.soaint.gestion.areas.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.soaint.gestion.areas.api.entity.TiposDocumentalesEntity;

@Repository
public interface TiposDocumentalesRepository  extends JpaRepository<TiposDocumentalesEntity, Integer> {

	
	List<TiposDocumentalesEntity> findBySeguridadSegregada(Integer seguridadSegregada);
	 @Query(
		      "SELECT td FROM TiposDocumentalesEntity td " +
		      "WHERE td.nombreOficinaProductora = :oficina " +
		      "  AND (td.serieDocumental   = :serie " +
		      "       OR td.subSerieDocumental = :serie) " +
		      "  AND td.etiqueta = :tipo"
		    )
		    Optional<TiposDocumentalesEntity> findValidTipoDocumental(
		        @Param("oficina") String nombreOficinaProductora,
		        @Param("serie")   String serieDocumental,
		        @Param("tipo")    String etiqueta
		    );

		    @Query(
		      "SELECT CASE WHEN COUNT(td)>0 THEN TRUE ELSE FALSE END " +
		      "FROM TiposDocumentalesEntity td " +
		      "WHERE td.nombreOficinaProductora = :oficina " +
		      "  AND (td.serieDocumental   = :serie " +
		      "       OR td.subSerieDocumental = :serie) " +
		      "  AND td.etiqueta = :tipo"
		    )
		    boolean existsValidTipoDocumental(
		        @Param("oficina") String nombreOficinaProductora,
		        @Param("serie")   String serieDocumental,
		        @Param("tipo")    String etiqueta
		    );

	
	
	
	
	
	
//    @Query(
//      "SELECT CASE WHEN COUNT(td) > 0 THEN TRUE ELSE FALSE END " +
//      "FROM TiposDocumentalesEntity td " +
//      "WHERE td.nombreOficinaProductora = :etiquetaArea " +
//      "  AND (td.subSerieDocumental = :categoria " +
//      "       OR td.serieDocumental = :categoria) " +
//      "  AND td.etiqueta = :tipoDocumental"
//    )
//    boolean existsValidTipoDocumental(
//        @Param("etiquetaArea")   String etiquetaArea,
//        @Param("categoria")      String categoria,
//        @Param("tipoDocumental") String tipoDocumental
//    );
//    
//    @Query(
//      "SELECT td " +
//      "FROM TiposDocumentalesEntity td " +
//      "WHERE td.nombreOficinaProductora = :etiquetaArea " +
//      "  AND (td.subSerieDocumental = :categoria " +
//      "       OR td.serieDocumental = :categoria) " +
//      "  AND td.etiqueta = :tipoDocumental"
//    )
//    Optional<TiposDocumentalesEntity> findValidTipoDocumental(
//        @Param("etiquetaArea")   String etiquetaArea,
//        @Param("categoria")      String categoria,
//        @Param("tipoDocumental") String tipoDocumental
//    );
    
    
    @Query("SELECT td FROM TiposDocumentalesEntity td " +
            "WHERE (:oficinaExacta IS NULL OR td.nombreOficinaProductora = :oficinaExacta) " +
            "  AND (:oficinaLike IS NULL OR UPPER(td.nombreOficinaProductora) LIKE CONCAT('%', UPPER(:oficinaLike), '%')) " +
            "  AND (:categoria IS NULL OR td.subSerieDocumental = :categoria OR td.serieDocumental = :categoria) " +
            "  AND (:tipoDoc IS NULL OR td.etiqueta = :tipoDoc) " +
            "  AND (:nombreDoc IS NULL OR UPPER(td.nombreDocumento) LIKE CONCAT('%', UPPER(:nombreDoc), '%'))")
     Page<TiposDocumentalesEntity> findByFiltros(@Param("oficinaExacta") String oficinaExacta,
                                                 @Param("oficinaLike")  String oficinaLike,
                                                 @Param("categoria")    String categoria,
                                                 @Param("tipoDoc")      String tipoDoc,
                                                 @Param("nombreDoc")    String nombreDoc,
                                                 Pageable pg);
    
    
}