package com.fonyou.pruebatecnica.examen.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "respuesta_estudiante")
@Getter
@Setter
@ToString
public class RespuestaEstudiante {

    @EmbeddedId
    private RespuestaEstudianteId id;

    @ManyToOne
    @MapsId("idEstudiante")
    @JoinColumn(name = "id_estudiante")
    private Estudiante estudiante;

    @ManyToOne
    @MapsId("idPregunta")
    @JoinColumn(name = "id_pregunta")
    private Pregunta pregunta;

    @Column(name = "opcion_elegida")
    private char opcionElegida;

}
