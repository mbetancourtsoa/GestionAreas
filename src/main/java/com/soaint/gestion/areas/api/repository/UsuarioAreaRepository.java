package com.soaint.gestion.areas.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.soaint.gestion.areas.api.entity.UsuarioAreaEntity;
import com.soaint.gestion.areas.api.entity.UsuarioAreaId;

@Repository
public interface UsuarioAreaRepository extends JpaRepository<UsuarioAreaEntity, UsuarioAreaId> {

    @Query("SELECT a.nombreOficinaProductora " +
           "FROM UsuarioAreaEntity ua " +
           "JOIN ua.area a " +
           "JOIN ua.usuario u " +
           "WHERE u.username = :username")
    String findAreaByUsername(@Param("username") String username);
}
