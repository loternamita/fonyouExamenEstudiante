package com.fonyou.pruebatecnica.examen.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity(name = "pregunta")
@Getter
@Setter
@ToString
public class Pregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_examen")
    private Examen examen;

    @Column
    private String enunciado;

    @Column(name = "opcion_correcta")
    private Character opcionCorrecta;

    @Column
    private int puntaje;

    @OneToMany(mappedBy = "pregunta")
    private Set<Opcion> opciones;

}
