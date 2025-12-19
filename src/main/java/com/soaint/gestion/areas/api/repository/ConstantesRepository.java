package com.soaint.gestion.areas.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soaint.gestion.areas.api.domain.Constantes;
import com.soaint.gestion.areas.api.entity.ConstantesEntity;

@Repository
public interface ConstantesRepository extends JpaRepository<ConstantesEntity,Integer> {
	  Optional<Constantes> findByCodigo(String codigo);
    List<ConstantesEntity> findByParentIdAndActivo(String parentId, Integer activo);
    
    boolean existsByParentIdAndCodigoAndActivo(String parentId, String codigo, Integer activo);

    List<ConstantesEntity> findByParentIdAndActivoOrderByCodigoAsc(String parentId, Integer activo);
    List<ConstantesEntity> findByParentIdAndActivoOrderByNombreAsc(String parentId, Integer activo);

}