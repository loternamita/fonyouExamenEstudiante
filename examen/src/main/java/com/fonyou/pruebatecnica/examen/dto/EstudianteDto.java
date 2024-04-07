package com.fonyou.pruebatecnica.examen.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EstudianteDto {
    private String nombre;
    private int edad;
    private String ciudad;
    private String zonaHoraria;
}