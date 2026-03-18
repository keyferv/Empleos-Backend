package com.example.empleos.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalTime;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "oferta_detalle")
public class OfertaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String requirements;
    private String responsibilities;
    private String benefits;
    private String location;

    @Column(name = "start_hour")
    private LocalTime startHour;

    @Column(name = "end_hour")
    private LocalTime endHour;

    @Column(name = "process_selection")
    private String processSelection;
    private BigDecimal salary;
}
