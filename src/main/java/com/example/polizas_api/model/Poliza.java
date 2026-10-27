package com.example.polizas_api.model;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "polizas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder

public class Poliza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPoliza tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPoliza estado;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal canon;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal prima;
    
    @OneToMany(mappedBy = "poliza", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Riesgo> riesgos = new ArrayList<>();
}
