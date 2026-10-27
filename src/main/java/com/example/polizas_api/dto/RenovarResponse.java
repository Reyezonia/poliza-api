package com.example.polizas_api.dto;

import com.example.polizas_api.model.EstadoPoliza;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RenovarResponse {
    private Long polizaId;
    private BigDecimal canonAnterior;
    private BigDecimal canonNuevo;
    private BigDecimal primaAnterior;
    private BigDecimal primaNueva;
    private EstadoPoliza estado;
    private String mensaje; 
}
