package com.soaint.gestion.areas.api.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soaint.gestion.areas.api.entity.UsuariosEntity;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuariosEntity, UUID> {
    Optional<UsuariosEntity> findByUsername(String username);
    
}
