package com.fonyou.pruebatecnica.examen.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity(name = "examen_estudiante")
@Getter
@Setter
@ToString
public class ExamenEstudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_estudiante")
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "id_examen")
    private Examen examen;

    @Column(name = "fecha_examen_zona_horaria")
    private LocalDateTime fechaExamenZonaHoraria;

    @Column(name = "calificacion")
    private int calificacion;

    @Column(name = "estado")
    private String estado;
}
