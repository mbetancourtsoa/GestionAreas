package com.soaint.gestion.areas.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soaint.gestion.areas.api.domain.Declaracion;

@Repository
public interface DeclaracionRepository extends JpaRepository<Declaracion,Integer> {
    List<Declaracion> findByEstadoCodigo(String codigo);      // PENDIENTE, ERROR…
    Page<Declaracion> findByEstado_Id(Integer idEstado, Pageable pageable);

}