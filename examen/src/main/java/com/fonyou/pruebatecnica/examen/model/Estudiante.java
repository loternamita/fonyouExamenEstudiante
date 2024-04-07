package com.fonyou.pruebatecnica.examen.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "estudiante")
@Getter
@Setter
@ToString
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String nombre;

    @Column
    private int edad;

    @Column
    private String ciudad;

    @Column(name = "zona_horaria")
    private String zonaHoraria;
}
