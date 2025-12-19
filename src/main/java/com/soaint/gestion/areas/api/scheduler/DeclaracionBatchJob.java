package com.soaint.gestion.areas.api.scheduler;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.soaint.gestion.areas.api.service.DeclaracionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeclaracionBatchJob {

    private final DeclaracionService service;

    /** 12 pm, 4 pm, 10 pm (horarios normales) */
    @Scheduled(cron = "0 0 12,16,22 * * *", zone = "America/Bogota")
    public void ejecucionDiaria() {
        log.info("Ejecutando lote diario de declaraciones…");
        service.procesarPendientes();
    }

    /** 1 am y 5 am (horarios de traslado masivo) */
    @Scheduled(cron = "0 0 1,5 * * *", zone = "America/Bogota")
    public void ejecucionTrasladoMasivo() {
        log.info("Ejecutando lote masivo de declaraciones…");
        service.procesarPendientes();
    }
}