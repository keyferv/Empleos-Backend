package com.example.empleos.persistence.repository;

import com.example.empleos.persistence.entity.EstadoType;
import com.example.empleos.persistence.entity.Vacantes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface VacanteRepository extends JpaRepository<Vacantes, Integer> {

    @Query("SELECT v FROM Vacantes v WHERE v.expirationDate < :currentDate AND v.estado.type = 'ACTIVO'")
    List<Vacantes> findExpiredActiveVacancies(@Param("currentDate") Date currentDate);

    @Query("SELECT v FROM Vacantes v WHERE v.estado.type = 'ACTIVO'")
    List<Vacantes> findActiveVacancies();

    @Query("SELECT v FROM Vacantes v WHERE v.featured = true AND v.estado.type = 'ACTIVO'")
    List<Vacantes> findFeaturedVacancies();

    @Query("SELECT v FROM Vacantes v JOIN v.categories c WHERE c.id = :categoryId AND v.estado.type = 'ACTIVO'")
    List<Vacantes> findByCategoryId(@Param("categoryId") Integer categoryId);

    @Query("SELECT v FROM Vacantes v WHERE v.offerName LIKE %:keyword% AND v.estado.type = 'ACTIVO'")
    List<Vacantes> findByOfferNameContaining(@Param("keyword") String keyword);
}