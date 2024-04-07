package com.fonyou.pruebatecnica.examen.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity(name = "examen")
@Getter
@Setter
@ToString
public class Examen {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "total_puntos")
    private int totalPuntos;

    @OneToMany(mappedBy = "examen")
    private Set<Pregunta> preguntas;
}
