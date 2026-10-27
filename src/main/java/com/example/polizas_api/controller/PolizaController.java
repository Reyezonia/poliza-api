package com.example.polizas_api.controller;

import com.example.polizas_api.dto.RenovarResponse;
import com.example.polizas_api.model.*;
import com.example.polizas_api.service.*;
import com.example.polizas_api.model.EstadoPoliza;
import com.example.polizas_api.model.Poliza;
import com.example.polizas_api.model.Riesgo;
import com.example.polizas_api.model.TipoPoliza;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/polizas")
@RequiredArgsConstructor
public class PolizaController {

    private final PolizaService polizaService;
    private final RiesgoService riesgoService;

    // 1. GET /polizas?tipo=...&estado=...
    @GetMapping
    public List<Poliza> listar(@RequestParam(required = false) TipoPoliza tipo,
                               @RequestParam(required = false) EstadoPoliza estado) {
        return polizaService.listar(tipo, estado);
    }
// 2. GET /polizas/{id}/riesgos
    @GetMapping("/{id}/riesgos")
    public List<Riesgo> listarRiesgos(@PathVariable Long id) {
        polizaService.obtenerPorId(id); // valida existencia
        return riesgoService.listarPorPoliza(id);
    }

    // 3. POST /polizas/{id}/renovar
    @PostMapping("/{id}/renovar")
    public ResponseEntity<RenovarResponse> renovar(@PathVariable Long id) {
        return ResponseEntity.ok(polizaService.renovar(id));
    }

    // 4. POST /polizas/{id}/cancelar
    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Poliza> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(polizaService.cancelar(id));
    }

    // 5. POST /polizas/{id}/riesgos  (solo si tipo = COLECTIVA)
    @PostMapping("/{id}/riesgos")
    public ResponseEntity<Riesgo> agregarRiesgo(@PathVariable Long id,
                                                @RequestBody Map<String, String> body) {
        String descripcion = body.get("descripcion");
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("El campo 'descripcion' es obligatorio");
        }
        return ResponseEntity.ok(polizaService.agregarRiesgo(id, descripcion));
    }
}
