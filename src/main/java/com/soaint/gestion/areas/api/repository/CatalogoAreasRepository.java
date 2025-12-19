package com.soaint.gestion.areas.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.soaint.gestion.areas.api.entity.AreasEntity;
@Repository
public interface CatalogoAreasRepository extends JpaRepository<AreasEntity, UUID> {


    @Query("SELECT a FROM AreasEntity a ORDER BY a.nombreOficinaProductora ASC")
    List<AreasEntity> findAllOrdenadas();
}