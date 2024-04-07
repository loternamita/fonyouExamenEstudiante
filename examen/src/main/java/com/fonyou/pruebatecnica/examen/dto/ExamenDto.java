package com.fonyou.pruebatecnica.examen.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Set;

@Data
@ToString
public class ExamenDto {
    private int totalPuntos;
    private Set<PreguntaDto> pregunta;
}
