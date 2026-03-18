package com.example.empleos.service.implementation;

import com.example.empleos.persistence.entity.Estado;
import com.example.empleos.persistence.entity.EstadoType;
import com.example.empleos.persistence.entity.Solicitudes;
import com.example.empleos.persistence.repository.SolicitudRepository;
import com.example.empleos.persistence.repository.EstadoRepository;
import com.example.empleos.service.interfaces.SolicitudServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SolicitudImpl implements SolicitudServices {

    private final SolicitudRepository solicitudRepository;
    private final EstadoRepository estadoRepository;

    @Override
    public List<Solicitudes> getAllSolicitudes() {
        return solicitudRepository.findAll();
    }

    @Override
    public Optional<Solicitudes> getSolicitudById(Integer id) {
        return solicitudRepository.findById(id);
    }

    @Override
    public List<Solicitudes> getSolicitudesByUserId(Integer userId) {
        return solicitudRepository.findByUserId(userId);
    }

    @Override
    public List<Solicitudes> getSolicitudesByVacancyId(Integer vacancyId) {
        return solicitudRepository.findByVacancyId(vacancyId);
    }

    @Override
    public Solicitudes createSolicitud(Solicitudes solicitud) {
        if (solicitud.getEstado() == null) {
            solicitud.setEstado(new Estado(EstadoType.PENDIENTE));
        }
        return solicitudRepository.save(solicitud);
    }

    @Override
    public Solicitudes updateSolicitudEstado(Integer id, String nuevoEstado) {
        Solicitudes solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada con id: " + id));
        
        Estado estado = estadoRepository.findByType(EstadoType.valueOf(nuevoEstado))
                .orElseThrow(() -> new RuntimeException("Estado no encontrado: " + nuevoEstado));
        
        solicitud.setEstado(estado);
        return solicitudRepository.save(solicitud);
    }

    @Override
    public void deleteSolicitud(Integer id) {
        solicitudRepository.deleteById(id);
    }
}