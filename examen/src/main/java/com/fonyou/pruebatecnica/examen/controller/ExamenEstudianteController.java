package com.fonyou.pruebatecnica.examen.controller;

import com.fonyou.pruebatecnica.examen.dto.ExamenEstudianteDto;
import com.fonyou.pruebatecnica.examen.service.ExamenEstudianteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/asignarExamen")
public class ExamenEstudianteController {

    private final ExamenEstudianteService examenEstudianteService;

    public ExamenEstudianteController(ExamenEstudianteService examenEstudianteService) {
        this.examenEstudianteService = examenEstudianteService;
    }

    @PostMapping("/asginar/{idExamen}")
    public ResponseEntity<ExamenEstudianteDto> asignarExamenAEstudiantes(
                                    @PathVariable Integer idExamen,
                                    @RequestBody Map<String, LocalDateTime> fechasPorZonaHoraria ){
        ExamenEstudianteDto response = examenEstudianteService.asignarExamenAEstudiante(idExamen, fechasPorZonaHoraria);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
