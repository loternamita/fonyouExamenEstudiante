package com.fonyou.pruebatecnica.examen.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class CalificacionExamenDto {
    private Long idEstudiante;
    private List<DetallesPreguntasDto> detallesPreguntas;
    private int calificacion;
    private int totalPuntosExamen;

}
