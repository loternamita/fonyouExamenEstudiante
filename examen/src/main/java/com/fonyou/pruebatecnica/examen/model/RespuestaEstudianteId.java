package com.fonyou.pruebatecnica.examen.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@ToString
public class RespuestaEstudianteId {

    private Long idEstudiante;
    private Long idPregunta;

    public RespuestaEstudianteId() {
    }

    public RespuestaEstudianteId(Long idEstudiante, Long idPregunta) {
        this.idEstudiante = idEstudiante;
        this.idPregunta = idPregunta;
    }
}
