package com.example.empleos.persistence.repository;

import com.example.empleos.persistence.entity.Solicitudes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitudes, Integer> {

    @Query("SELECT s FROM Solicitudes s WHERE s.usuario.id = :userId ORDER BY s.requestDate DESC")
    List<Solicitudes> findByUserId(@Param("userId") Integer userId);

    @Query("SELECT s FROM Solicitudes s WHERE s.vacancy.id = :vacancyId ORDER BY s.requestDate DESC")
    List<Solicitudes> findByVacancyId(@Param("vacancyId") Integer vacancyId);

    @Query("SELECT s FROM Solicitudes s WHERE s.usuario.id = :userId AND s.vacancy.id = :vacancyId")
    Optional<Solicitudes> findByUserIdAndVacancyId(@Param("userId") Integer userId, @Param("vacancyId") Integer vacancyId);
}