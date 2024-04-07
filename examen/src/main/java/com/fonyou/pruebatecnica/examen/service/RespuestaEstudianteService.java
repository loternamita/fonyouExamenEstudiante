package com.fonyou.pruebatecnica.examen.service;

import com.fonyou.pruebatecnica.examen.dto.CalificacionExamenDto;
import com.fonyou.pruebatecnica.examen.dto.RespuestaEstudianteDto;

public interface RespuestaEstudianteService {
    CalificacionExamenDto recopilarYCalificarRespuestas(RespuestaEstudianteDto respuestaEstudianteDto);
}
