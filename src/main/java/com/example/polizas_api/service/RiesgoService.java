package com.example.polizas_api.service;

import com.example.polizas_api.model.Poliza;
import com.example.polizas_api.model.Riesgo;
import com.example.polizas_api.repository.RiesgoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiesgoService {

    private final RiesgoRepository riesgoRepository;

    public List<Riesgo> listarPorPoliza(Long polizaId) {
        return riesgoRepository.findByPolizaId(polizaId);
    }

    @Transactional
    public Riesgo crearRiesgo(Poliza poliza, String descripcion) {
        Riesgo riesgo = Riesgo.builder()
                .descripcion(descripcion)
                .activo(true)
                .poliza(poliza)
                .build();
        return riesgoRepository.save(riesgo);
    }

    @Transactional
    public Riesgo cancelar(Long riesgoId) {
        Riesgo riesgo = riesgoRepository.findById(riesgoId)
                .orElseThrow(() -> new IllegalArgumentException("Riesgo no encontrado: " + riesgoId));
        if (Boolean.FALSE.equals(riesgo.getActivo())) {
            throw new IllegalStateException("El riesgo ya está cancelado");
        }
        riesgo.setActivo(false);
        riesgoRepository.save(riesgo);
        log.info("Riesgo {} cancelado", riesgoId);
        return riesgo;
    }
}