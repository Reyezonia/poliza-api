package com.example.polizas_api.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ErrorResponse {
    private int status;
    private String error;
    private String mensaje;
    
}
