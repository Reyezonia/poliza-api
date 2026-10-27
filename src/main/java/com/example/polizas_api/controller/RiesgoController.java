package com.example.polizas_api.controller;


import com.example.polizas_api.model.Riesgo;
import com.example.polizas_api.service.RiesgoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/riesgos")
@RequiredArgsConstructor
public class RiesgoController {

    private final RiesgoService riesgoService;

    // 6. POST /riesgos/{id}/cancelar
    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Riesgo> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(riesgoService.cancelar(id));
    }    
}
