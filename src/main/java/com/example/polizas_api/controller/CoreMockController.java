package com.example.polizas_api.controller;

import com.example.polizas_api.dto.CoreEventoRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/core-mock")
@RequiredArgsConstructor
@Slf4j
public class CoreMockController {

    @PostMapping("/evento")
    public ResponseEntity<Map<String, Object>> recibirEvento(@Valid @RequestBody CoreEventoRequest request) {
        log.info(">>> [CORE-MOCK] Evento recibido: evento={}, polizaId={}",
                request.getEvento(), request.getPolizaId());
        return ResponseEntity.ok(Map.of(
                "recibido", true,
                "evento", request.getEvento(),
                "polizaId", request.getPolizaId()));
    }  
}
