package com.example.empleos.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Vacantes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "offer_name")
    private String offerName;
    
    private String description;
    private Date date;
    
    @Column(name = "expiration_date")
    private Date expirationDate;

    private BigDecimal salary;
    private Boolean featured;
    @Builder.Default
    private String image = "no-image.png";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_id", nullable = false)
    private Estado estado;

    // 🟢 Al crear una vacante, se establece la fecha y la expiración automáticamente
    @PrePersist
    public void prePersist() {
        if (date == null) {
            this.date = new Date();
        }
        // Expiración por defecto: 30 días si no se pone una
        if (expirationDate == null) {
            this.expirationDate = new Date(this.date.getTime() + (30L * 24 * 60 * 60 * 1000));
        }
    }

    @OneToMany(mappedBy = "vacancy", cascade = CascadeType.ALL)
    @Builder.Default
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
    @Builder.Default
    private Set<Categoria> categories = new HashSet<>();
}
