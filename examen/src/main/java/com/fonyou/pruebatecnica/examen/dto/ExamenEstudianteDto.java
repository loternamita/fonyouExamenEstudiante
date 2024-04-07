package com.fonyou.pruebatecnica.examen.dto;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Data
@ToString
public class ExamenEstudianteDto {

    private List<String> mensajes;

    public ExamenEstudianteDto() {
        this.mensajes = new ArrayList<>();
    }

    public void agregarMensaje(String mensaje) {
        mensajes.add(mensaje);
    }

}
