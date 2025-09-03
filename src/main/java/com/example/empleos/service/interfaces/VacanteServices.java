package com.example.empleos.service.interfaces;

import com.example.empleos.persistence.entity.Vacantes;

import java.util.List;

public interface VacanteServices {

    // CREATE
    public void save(Vacantes vacantes);

    // READ
    public List<Vacantes> findAll();

    //BUSCAR POR EL NOMBRE DE LA OFERTA
    public Vacantes findByOfferName(String offerName);

    // TRAR UN ALISTA POR LA FECHA
    public List<Vacantes> findByDate(String date);
}
