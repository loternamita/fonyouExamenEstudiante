package com.fonyou.pruebatecnica.examen.dto;

import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@ToString
public class RespuestaEstudianteDto {
    private Long idEstudiante;
    private Long idPregunta;
    private Map<Long, Character> respuestas;
}
