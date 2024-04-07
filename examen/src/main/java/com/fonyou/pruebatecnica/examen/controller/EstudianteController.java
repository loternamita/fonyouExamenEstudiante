package com.fonyou.pruebatecnica.examen.controller;

import com.fonyou.pruebatecnica.examen.dto.EstudianteDto;
import com.fonyou.pruebatecnica.examen.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService){
        this.estudianteService = estudianteService;
    }

    @PostMapping("/guardarEstudiante")
    public ResponseEntity<EstudianteDto> crearEstudiante(@RequestBody EstudianteDto estudianteDto){
        EstudianteDto resEstudianteDto = estudianteService.crearEstudiante(estudianteDto);
        return new ResponseEntity<>(resEstudianteDto, HttpStatus.CREATED);
    }
}
