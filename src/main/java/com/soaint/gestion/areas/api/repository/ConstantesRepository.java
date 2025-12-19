package com.soaint.gestion.areas.api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.soaint.gestion.areas.api.domain.Constantes;

public interface ConstantesRepository extends JpaRepository<Constantes,Integer> {
    Optional<Constantes> findByCodigo(String codigo);
}