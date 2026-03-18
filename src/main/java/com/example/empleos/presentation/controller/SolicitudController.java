package com.example.empleos.presentation.controller;

import com.example.empleos.persistence.entity.Solicitudes;
import com.example.empleos.service.interfaces.SolicitudServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/solicitudes")
@AllArgsConstructor
public class SolicitudController {

    private final SolicitudServices solicitudService;

    @PostMapping
    public ResponseEntity<Solicitudes> createSolicitud(@RequestBody Solicitudes solicitud) {
        Solicitudes createdSolicitud = solicitudService.createSolicitud(solicitud);
        return new ResponseEntity<>(createdSolicitud, HttpStatus.CREATED);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Solicitudes>> getSolicitudesByUserId(@PathVariable Integer idUsuario) {
        List<Solicitudes> solicitudes = solicitudService.getSolicitudesByUserId(idUsuario);
        return new ResponseEntity<>(solicitudes, HttpStatus.OK);
    }

    @GetMapping("/vacante/{idVacante}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SuperVisor')")
    public ResponseEntity<List<Solicitudes>> getSolicitudesByVacancyId(@PathVariable Integer idVacante) {
        List<Solicitudes> solicitudes = solicitudService.getSolicitudesByVacancyId(idVacante);
        return new ResponseEntity<>(solicitudes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitudes> getSolicitudById(@PathVariable Integer id) {
        Optional<Solicitudes> solicitud = solicitudService.getSolicitudById(id);
        return solicitud.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/estado")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SuperVisor')")
    public ResponseEntity<Solicitudes> updateSolicitudEstado(@PathVariable Integer id, @RequestParam String estado) {
        try {
            Solicitudes updatedSolicitud = solicitudService.updateSolicitudEstado(id, estado);
            return new ResponseEntity<>(updatedSolicitud, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SuperVisor')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSolicitud(@PathVariable Integer id) {
        try {
            solicitudService.deleteSolicitud(id);
            return ResponseEntity.ok("Solicitud eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar la solicitud");
        }
    }
}