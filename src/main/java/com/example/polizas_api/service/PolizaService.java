package com.example.polizas_api.service;

import com.example.polizas_api.dto.CoreEventoRequest;
import com.example.polizas_api.dto.RenovarResponse;
import com.example.polizas_api.model.*;
import com.example.polizas_api.repository.PolizaRepository;
import com.example.polizas_api.model.EstadoPoliza;
import com.example.polizas_api.model.Poliza;
import com.example.polizas_api.model.Riesgo;
import com.example.polizas_api.model.TipoPoliza;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PolizaService {

    private final PolizaRepository polizaRepository;
    private final RiesgoService riesgoService;
    private final CoreMockService coreMockService;

    @Value("${app.ipc}")
    private BigDecimal ipc;

    public List<Poliza> listar(TipoPoliza tipo, EstadoPoliza estado) {
        if (tipo != null && estado != null) {
            return polizaRepository.findByTipoAndEstado(tipo, estado);
        }
        if (tipo != null) return polizaRepository.findByTipo(tipo);
        if (estado != null) return polizaRepository.findByEstado(estado);
        return polizaRepository.findAll();
    }  
    public Poliza obtenerPorId(Long id) {
        return polizaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Póliza no encontrada: " + id));
    }

    @Transactional
    public RenovarResponse renovar(Long id) {
        Poliza poliza = obtenerPorId(id);

        if (poliza.getEstado() == EstadoPoliza.CANCELADA) {
            throw new IllegalStateException("No se puede renovar una póliza cancelada");
        }

        BigDecimal factor = BigDecimal.ONE.add(ipc);

        BigDecimal canonAnterior = poliza.getCanon();
        BigDecimal primaAnterior = poliza.getPrima();

        BigDecimal canonNuevo = canonAnterior.multiply(factor).setScale(2, RoundingMode.HALF_UP);
        BigDecimal primaNueva = primaAnterior.multiply(factor).setScale(2, RoundingMode.HALF_UP);

        poliza.setCanon(canonNuevo);
        poliza.setPrima(primaNueva);
        poliza.setEstado(EstadoPoliza.RENOVADA);
        polizaRepository.save(poliza);

        coreMockService.enviarEvento(
                CoreEventoRequest.builder()
                        .evento("ACTUALIZACION")
                        .polizaId(id)
                        .build());
                         log.info("Póliza {} renovada: canon {} -> {}, prima {} -> {}",
                id, canonAnterior, canonNuevo, primaAnterior, primaNueva);

        return RenovarResponse.builder()
                .polizaId(id)
                .canonAnterior(canonAnterior)
                .canonNuevo(canonNuevo)
                .primaAnterior(primaAnterior)
                .primaNueva(primaNueva)
                .estado(poliza.getEstado())
                .mensaje("Póliza renovada correctamente")
                .build();
    }

    @Transactional
    public Poliza cancelar(Long id) {
        Poliza poliza = obtenerPorId(id);
        if (poliza.getEstado() == EstadoPoliza.CANCELADA) {
            throw new IllegalStateException("La póliza ya está cancelada");
        }
        poliza.setEstado(EstadoPoliza.CANCELADA);
        polizaRepository.save(poliza);

        coreMockService.enviarEvento(
                CoreEventoRequest.builder()
                        .evento("CANCELACION")
                        .polizaId(id)
                        .build());

        return poliza;
    }
      @Transactional
    public Riesgo agregarRiesgo(Long polizaId, String descripcion) {
        Poliza poliza = obtenerPorId(polizaId);

        if (poliza.getTipo() != TipoPoliza.COLECTIVA) {
            throw new IllegalStateException("Solo se pueden agregar riesgos a pólizas tipo COLECTIVA");
        }
        if (poliza.getEstado() == EstadoPoliza.CANCELADA) {
            throw new IllegalStateException("No se pueden agregar riesgos a pólizas canceladas");
        }

        return riesgoService.crearRiesgo(poliza, descripcion);
    }
}
