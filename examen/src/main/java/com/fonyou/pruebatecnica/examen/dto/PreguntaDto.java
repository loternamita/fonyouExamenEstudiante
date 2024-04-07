package com.fonyou.pruebatecnica.examen.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Set;

@Data
@ToString
public class PreguntaDto {
    private String enunciado;
    private Character opcionCorrecta;
    private int puntaje;
    private Set<OpcionDto> opciones;
}
