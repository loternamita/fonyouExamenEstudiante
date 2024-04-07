package com.fonyou.pruebatecnica.examen.controller;

import com.fonyou.pruebatecnica.examen.dto.ExamenDto;
import com.fonyou.pruebatecnica.examen.service.ExamenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/examenes")
public class ExamenController {

    private final ExamenService examenService;

    public ExamenController(ExamenService examenService){
        this.examenService = examenService;
    }

    @PostMapping("/crearExamen")
    public ResponseEntity<ExamenDto> crearExamen(@RequestBody ExamenDto examenDto){
        ExamenDto resExamenDto = examenService.crearExamen(examenDto);
        return new ResponseEntity<>(resExamenDto, HttpStatus.CREATED);
    }
}
