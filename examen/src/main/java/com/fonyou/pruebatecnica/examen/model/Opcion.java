package com.fonyou.pruebatecnica.examen.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "opcion")
@Getter
@Setter
@ToString
public class Opcion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_pregunta")
    private Pregunta pregunta;

    @Column
    private Character opcion;

    @Column
    private String texto;
}
