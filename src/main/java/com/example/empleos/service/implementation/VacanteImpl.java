package com.example.empleos.service.implementation;

import com.example.empleos.persistence.entity.Estado;
import com.example.empleos.persistence.entity.EstadoType;
import com.example.empleos.persistence.entity.Vacantes;
import com.example.empleos.persistence.repository.EstadoRepository;
import com.example.empleos.persistence.repository.VacanteRepository;
import com.example.empleos.service.interfaces.VacanteServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VacanteImpl implements VacanteServices {

    private final VacanteRepository vacanteRepository;
    private final EstadoRepository estadoRepository;

    @Override
    public List<Vacantes> getAllVacantes() {
        return vacanteRepository.findActiveVacancies();
    }

    @Override
    public Optional<Vacantes> getVacanteById(Integer id) {
        return vacanteRepository.findById(id);
    }
    
    // ... otros métodos ...

    @Override
    public List<Vacantes> getActiveVacancies() {
        return vacanteRepository.findActiveVacancies();
    }

    @Override
    public List<Vacantes> getFeaturedVacancies() {
        return vacanteRepository.findFeaturedVacancies();
    }

    @Override
    public List<Vacantes> getVacanciesByCategory(Integer categoryId) {
        return vacanteRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Vacantes> searchVacanciesByName(String keyword) {
        return vacanteRepository.findByOfferNameContaining(keyword);
    }

    @Override
    public Vacantes createVacante(Vacantes vacante) {
        return vacanteRepository.save(vacante);
    }

    @Override
    public Vacantes updateVacante(Integer id, Vacantes vacanteDetails) {
        Vacantes existingVacante = vacanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vacante no encontrada con id: " + id));
        
        existingVacante.setOfferName(vacanteDetails.getOfferName());
        existingVacante.setDescription(vacanteDetails.getDescription());
        existingVacante.setDate(vacanteDetails.getDate());
        existingVacante.setSalary(vacanteDetails.getSalary());
        existingVacante.setFeatured(vacanteDetails.getFeatured());
        existingVacante.setImage(vacanteDetails.getImage());
        existingVacante.setEstado(vacanteDetails.getEstado());
        existingVacante.setCategories(vacanteDetails.getCategories());
        
        return vacanteRepository.save(existingVacante);
    }

    @Override
    public Vacantes closeVacante(Integer id) {
        Vacantes existingVacante = vacanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vacante no encontrada con id: " + id));
        
        Estado estadoCerrado = estadoRepository.findByType(EstadoType.CERRADO)
                .orElseGet(() -> estadoRepository.save(new Estado(EstadoType.CERRADO)));
        
        existingVacante.setEstado(estadoCerrado);
        return vacanteRepository.save(existingVacante);
    }

    @Override
    public void deleteVacante(Integer id) {
        vacanteRepository.deleteById(id);
    }
}