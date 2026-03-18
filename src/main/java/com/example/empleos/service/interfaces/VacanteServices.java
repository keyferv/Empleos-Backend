package com.example.empleos.service.interfaces;

import com.example.empleos.persistence.entity.Vacantes;

import java.util.List;
import java.util.Optional;

public interface VacanteServices {
    List<Vacantes> getAllVacantes();
    Optional<Vacantes> getVacanteById(Integer id);
    List<Vacantes> getActiveVacancies();
    List<Vacantes> getFeaturedVacancies();
    List<Vacantes> getVacanciesByCategory(Integer categoryId);
    List<Vacantes> searchVacanciesByName(String keyword);
    Vacantes createVacante(Vacantes vacante);
    Vacantes updateVacante(Integer id, Vacantes vacanteDetails);
    Vacantes closeVacante(Integer id);
    void deleteVacante(Integer id);
}