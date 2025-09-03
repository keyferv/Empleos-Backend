package com.example.empleos.persistence.entity;

import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
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
    private String startHour;

    @Column(name = "end_hour")
    private String endHour;

    @Column(name = "process_selection")
    private String processSelection;
    private String salary;
}
