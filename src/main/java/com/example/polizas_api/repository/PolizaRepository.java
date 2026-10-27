package com.example.polizas_api.repository;

import com.example.polizas_api.model.EstadoPoliza;
import com.example.polizas_api.model.Poliza;
import com.example.polizas_api.model.TipoPoliza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PolizaRepository extends JpaRepository<Poliza, Long> {
    List<Poliza> findByTipoAndEstado(TipoPoliza tipo, EstadoPoliza estado);
    List<Poliza> findByTipo(TipoPoliza tipo);
    List<Poliza> findByEstado(EstadoPoliza estado);
    
}
