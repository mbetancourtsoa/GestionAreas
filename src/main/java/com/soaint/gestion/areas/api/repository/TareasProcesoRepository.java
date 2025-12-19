package com.soaint.gestion.areas.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soaint.gestion.areas.api.entity.TareasProcesoEntity;

public interface TareasProcesoRepository extends JpaRepository<TareasProcesoEntity, UUID> {
    List<TareasProcesoEntity> findByIdInstanciaOrderByOrdenEjecucionAsc(UUID idInstancia);
    List<TareasProcesoEntity> findByEstado(String estado);
    List<TareasProcesoEntity> findByNombreTarea(String nombreTarea);
}
