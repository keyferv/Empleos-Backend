package com.example.empleos.presentation.controller;

import com.example.empleos.persistence.entity.Vacantes;
import com.example.empleos.service.interfaces.VacanteServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vacantes")
@AllArgsConstructor
public class VacanteController {

    private final VacanteServices vacanteService;

    @GetMapping
    public ResponseEntity<List<Vacantes>> getAllVacantes() {
        List<Vacantes> vacantes = vacanteService.getAllVacantes();
        return new ResponseEntity<>(vacantes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacantes> getVacanteById(@PathVariable Integer id) {
        Optional<Vacantes> vacante = vacanteService.getVacanteById(id);
        return vacante.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/activas")
    public ResponseEntity<List<Vacantes>> getActiveVacancies() {
        List<Vacantes> activeVacancies = vacanteService.getActiveVacancies();
        return new ResponseEntity<>(activeVacancies, HttpStatus.OK);
    }

    @GetMapping("/destacadas")
    public ResponseEntity<List<Vacantes>> getFeaturedVacancies() {
        List<Vacantes> featuredVacancies = vacanteService.getFeaturedVacancies();
        return new ResponseEntity<>(featuredVacancies, HttpStatus.OK);
    }

    @GetMapping("/categoria/{categoryId}")
    public ResponseEntity<List<Vacantes>> getVacanciesByCategory(@PathVariable Integer categoryId) {
        List<Vacantes> vacancies = vacanteService.getVacanciesByCategory(categoryId);
        return new ResponseEntity<>(vacancies, HttpStatus.OK);
    }

    @GetMapping("/buscar/{keyword}")
    public ResponseEntity<List<Vacantes>> searchVacancies(@PathVariable String keyword) {
        List<Vacantes> vacancies = vacanteService.searchVacanciesByName(keyword);
        return new ResponseEntity<>(vacancies, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Vacantes> createVacante(@RequestBody Vacantes vacante) {
        Vacantes createdVacante = vacanteService.createVacante(vacante);
        return new ResponseEntity<>(createdVacante, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Vacantes> updateVacante(@PathVariable Integer id, @RequestBody Vacantes vacanteDetails) {
        try {
            Vacantes updatedVacante = vacanteService.updateVacante(id, vacanteDetails);
            return new ResponseEntity<>(updatedVacante, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/cerrar")
    public ResponseEntity<Vacantes> closeVacante(@PathVariable Integer id) {
        try {
            Vacantes closedVacante = vacanteService.closeVacante(id);
            return new ResponseEntity<>(closedVacante, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVacante(@PathVariable Integer id) {
        try {
            vacanteService.deleteVacante(id);
            return ResponseEntity.ok("Vacante eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar la vacante");
        }
    }
}