package com.example.empleos.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Vacantes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "offer_name")
    private String offerName;
    
    private String description;
    private Date date;
    private Double salary;
    private Integer featured;
    private String image = "no-image.png";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_id", nullable = false)
    private Estado estado;

    @OneToMany(mappedBy = "vacancy", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Solicitudes> requests = new HashSet<>();

    // Una vacante tiene una oferta de detalle - OneToOne
    @OneToOne(fetch = FetchType.LAZY, optional = false, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_detalle", referencedColumnName = "id")
    private OfertaDetalle detail;

    @ManyToMany
    @JoinTable(
            name = "vacante_categoria",
            joinColumns = @JoinColumn(name = "vacante_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private Set<Categoria> categories = new HashSet<>();
}
