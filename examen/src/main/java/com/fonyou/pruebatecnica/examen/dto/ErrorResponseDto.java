package com.fonyou.pruebatecnica.examen.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ErrorResponseDto {
    private String mensaje;
    private String Detalles;
}
