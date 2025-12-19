package com.soaint.gestion.areas.api.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soaint.gestion.areas.api.domain.Declaracion;
import com.soaint.gestion.areas.api.domain.ErrorDeclaracion;
import com.soaint.gestion.areas.api.domain.EstadoDeclaracion;
import com.soaint.gestion.areas.api.dto.DeclaracionRequest;
import com.soaint.gestion.areas.api.repository.DeclaracionRepository;
import com.soaint.gestion.areas.api.repository.ErrorDeclaracionRepository;
import com.soaint.gestion.areas.api.repository.EstadoDeclaracionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional          // ← usa la anotación de Spring, no la de javax.transaction
public class DeclaracionServiceImpl implements DeclaracionService {

    private final DeclaracionRepository declaracionRepo;
    private final EstadoDeclaracionRepository estadoRepo;
    private final ErrorDeclaracionRepository errorRepo;
    
    private static final Integer ESTADO_PENDIENTE_ID = 4;   // tabla EstadoDeclaracion
    private static final Integer ESTADO_ERROR_ID = 3;  // ERROR
    
    
    @Override
    @Transactional(readOnly = true)
    public Page<Declaracion> obtenerErrores(Pageable pageable) {
        return declaracionRepo.findByEstado_Id(ESTADO_ERROR_ID, pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<Declaracion> obtenerPendientes(Pageable pageable) {
        return declaracionRepo.findByEstado_Id(ESTADO_PENDIENTE_ID, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Declaracion> obtenerPendientes() {
        return declaracionRepo.findByEstadoCodigo("PENDIENTE");
    }

    /* --------------------------------------------------  helpers  -------- */
    private EstadoDeclaracion estado(String cod) {
        return estadoRepo.findByCodigo(cod)
                .orElseThrow(() -> new IllegalStateException("Estado no configurado: " + cod));
    }

    /* --------------------------------------------------  API pública ------ */
    @Override
    public Declaracion registrarEventoDocumento(DeclaracionRequest dto) {
        Declaracion d = new Declaracion();
        d.setIdDocumento(dto.getIdDocumento());
        d.setNombreDocumento(dto.getNombreDocumento());
        d.setRutaExpediente(dto.getRutaExpediente());
        d.setObjectStore(dto.getObjectStore());
        d.setFechaCreacion(LocalDateTime.now());
        d.setTipoTraslado(dto.getMetodoTraslado());
        d.setEstado(estado("PENDIENTE"));        // ← IdEstado = 4
        return declaracionRepo.save(d);
    }

    @Override
    @Transactional(noRollbackFor = Exception.class)
    public void procesarPendientes() {
        List<Declaracion> pendientes = declaracionRepo.findByEstadoCodigo("PENDIENTE");
        pendientes.forEach(this::intentarDeclarar);
    }

    @Override
    public void reintentar(Integer idDeclaracion) {
        Declaracion d = declaracionRepo.findById(idDeclaracion)
                .orElseThrow(EntityNotFoundException::new);
        d.setEstado(estado("PENDIENTE"));
    }

    /* --------------------------------------------------  core interna ----- */
    private void intentarDeclarar(Declaracion d) {
        try {
            d.setEstado(estado("EN_PROCESO"));

            /*  Aquí irá la lógica real cuando tengas que integrarte con
                FileNet CE / Records Manager o cualquier otro sistema.
                Por ahora solo marcamos como DECLARADO.  */

            d.setEstado(estado("DECLARADO"));
            d.setFechaEjecucion(LocalDateTime.now());

        } catch (Exception e) {        // cualquier error inesperado
            registrarError(d, "GENERAL", e.getMessage(), null);
            throw e;
        }
    }

    private void registrarError(Declaracion d, String tipo, String mensaje, String traceId) {
        d.setEstado(estado("ERROR"));
        errorRepo.save(
            new ErrorDeclaracion(null, d, tipo, mensaje, traceId, LocalDateTime.now())
        );
    }
    

    
    @Override
    @Transactional
    public void declararSeleccionados(List<Integer> ids) {
        List<Declaracion> lista = declaracionRepo.findAllById(ids);
        EstadoDeclaracion pendiente = estadoRepo.findByCodigo("PENDIENTE")
            .orElseThrow(() -> new IllegalStateException("Estado DECLARADO no configurado"));
        LocalDateTime ahora = LocalDateTime.now();

        lista.forEach(d -> {
            d.setEstado(pendiente);
            d.setFechaEjecucion(ahora);
        });

        declaracionRepo.saveAll(lista);
        System.out.println(">>> declaracionRepo.saveAll ENTIDADES = " + lista.size());
    }

}
