package com.example.empleos.persistence.repository;

import com.example.empleos.persistence.entity.Estado;
import com.example.empleos.persistence.entity.Vacantes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface VacanteRepository extends JpaRepository<Vacantes, Integer> {

    List<Vacantes> findByEstado(Estado estado);

    List<Vacantes> findByEstadoAndFeatured(Estado estado, Boolean featured);

    List<Vacantes> findByEstadoOrderByDateDesc(Estado estado);  // Corregido el "DescDesc"

    List<Vacantes> findByFeaturedAfterOrderByDateDesc(Boolean featuredAfter);  // Corregido el "OrderByFechaDescOrderByFechaDesc"

    List<Vacantes> findByFeaturedAndEstadoOrderByIdDesc(Boolean featured, Estado estado);

    List<Vacantes> findByDate(Date date);

    List<Vacantes> findBySalary(BigDecimal salary);
}
