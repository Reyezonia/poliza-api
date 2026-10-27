package com.example.polizas_api.service;


import com.example.polizas_api.dto.CoreEventoRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CoreMockService {

    /**
     * Simula el envío de un evento al CORE.
     * Su único propósito es registrar en logs que la operación se intentó enviar.
     */
    public void enviarEvento(CoreEventoRequest request) {
        log.info(">>> [CORE-MOCK] Enviando evento al CORE: evento={}, polizaId={}",
                request.getEvento(), request.getPolizaId());
    } 
}
