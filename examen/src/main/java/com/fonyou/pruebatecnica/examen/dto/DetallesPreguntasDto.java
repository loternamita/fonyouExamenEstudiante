package com.fonyou.pruebatecnica.examen.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DetallesPreguntasDto {
    private Long idPregunta;
    private String enunciado;
    private Character opcionCorrecta;
    private Character opcionElegida;
    private int puntajeObtenido;

    public DetallesPreguntasDto(Long id, String enunciado, Character value, int puntajeObtenido, Character opcionCorrecta) {
        this.idPregunta = id;
        this.enunciado = enunciado;
        this.opcionElegida = value;
        this.puntajeObtenido = puntajeObtenido;
        this.opcionCorrecta = opcionCorrecta;

    }
}
