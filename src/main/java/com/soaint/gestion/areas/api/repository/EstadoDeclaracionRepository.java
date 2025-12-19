package com.soaint.gestion.areas.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soaint.gestion.areas.api.domain.EstadoDeclaracion;

@Repository
public interface EstadoDeclaracionRepository
        extends JpaRepository<EstadoDeclaracion,Integer> {

    Optional<EstadoDeclaracion> findByCodigo(String codigo);
}