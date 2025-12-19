package com.soaint.gestion.areas.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soaint.gestion.areas.api.domain.ErrorDeclaracion;

@Repository
public interface ErrorDeclaracionRepository
        extends JpaRepository<ErrorDeclaracion,Integer> { 
	List<ErrorDeclaracion> findByFechaErrorBetween(LocalDateTime d, LocalDateTime h);
    List<ErrorDeclaracion> findByDeclaracion_Id(Integer idDeclaracion);


}