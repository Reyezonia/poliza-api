package com.example.polizas_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CoreEventoRequest {
      @NotBlank
    private String evento;
    @NotNull
    private Long polizaId;
}
