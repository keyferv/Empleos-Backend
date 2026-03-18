package com.example.empleos.service.interfaces;

import com.example.empleos.persistence.entity.Solicitudes;

import java.util.List;
import java.util.Optional;

public interface SolicitudServices {
    List<Solicitudes> getAllSolicitudes();
    Optional<Solicitudes> getSolicitudById(Integer id);
    List<Solicitudes> getSolicitudesByUserId(Integer userId);
    List<Solicitudes> getSolicitudesByVacancyId(Integer vacancyId);
    Solicitudes createSolicitud(Solicitudes solicitud);
    Solicitudes updateSolicitudEstado(Integer id, String nuevoEstado);
    void deleteSolicitud(Integer id);
}