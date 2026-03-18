package com.example.empleos.service.tasks;

import com.example.empleos.persistence.entity.Estado;
import com.example.empleos.persistence.entity.EstadoType;
import com.example.empleos.persistence.entity.Vacantes;
import com.example.empleos.persistence.repository.EstadoRepository;
import com.example.empleos.persistence.repository.VacanteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class VacanteTask {

    @Autowired
    private VacanteRepository vacanteRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    /**
     * Tarea programada que se ejecuta cada hora para cerrar vacantes expiradas.
     * fixedRate = 3600000ms (1 hora)
     */
    @Scheduled(fixedRate = 3600000)
    public void closeExpiredVacancies() {
        log.info("Iniciando tarea programada: Cierre automático de vacantes expiradas...");
        
        Date now = new Date();
        List<Vacantes> expiredOnes = vacanteRepository.findExpiredActiveVacancies(now);
        
        if (expiredOnes.isEmpty()) {
            log.info("No se encontraron vacantes expiradas para procesar.");
            return;
        }

        Estado estadoExpirado = estadoRepository.findByType(EstadoType.EXPIRADO)
                .orElseGet(() -> estadoRepository.save(new Estado(EstadoType.EXPIRADO)));

        for (Vacantes v : expiredOnes) {
            log.info("Cerrando vacante: {} (ID: {}) por expiración.", v.getOfferName(), v.getId());
            v.setEstado(estadoExpirado);
        }

        vacanteRepository.saveAll(expiredOnes);
        log.info("Proceso de cierre automático finalizado. {} vacantes actualizadas.", expiredOnes.size());
    }
}
