package com.fonyou.pruebatecnica.examen.controller;

import com.fonyou.pruebatecnica.examen.dto.CalificacionExamenDto;
import com.fonyou.pruebatecnica.examen.dto.RespuestaEstudianteDto;
import com.fonyou.pruebatecnica.examen.service.RespuestaEstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/respuestaEstudiante")
public class RespuestaEstudianteController {

    private final RespuestaEstudianteService respuestaEstudianteService;

    public RespuestaEstudianteController(RespuestaEstudianteService respuestaEstudianteService){
        this.respuestaEstudianteService = respuestaEstudianteService;
    }

    @PostMapping("/respuestaYCalificacion")
    public ResponseEntity<CalificacionExamenDto> recopilarYCalificarRespuestas(@RequestBody RespuestaEstudianteDto respuestaEstudianteDto){
        CalificacionExamenDto response = respuestaEstudianteService.recopilarYCalificarRespuestas(respuestaEstudianteDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
