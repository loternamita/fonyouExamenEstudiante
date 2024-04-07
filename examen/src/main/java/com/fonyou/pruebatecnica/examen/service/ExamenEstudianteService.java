package com.fonyou.pruebatecnica.examen.service;

import com.fonyou.pruebatecnica.examen.dto.ExamenEstudianteDto;

import java.time.LocalDateTime;
import java.util.Map;

public interface ExamenEstudianteService {
    ExamenEstudianteDto asignarExamenAEstudiante(Integer idExamen, Map<String, LocalDateTime> fechasPorZonaHoraria);
}
