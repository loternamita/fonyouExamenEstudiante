package com.fonyou.pruebatecnica.examen.controller;

import com.fonyou.pruebatecnica.examen.dto.EstudianteDto;
import com.fonyou.pruebatecnica.examen.service.EstudianteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class EstudianteControllerTest {

    @Mock
    private EstudianteService estudianteService;

    @InjectMocks
    private EstudianteController estudianteController;

    private EstudianteDto estudianteDto;

    @BeforeEach
    void setUp() {
        estudianteDto = new EstudianteDto();
        estudianteDto.setNombre("Andres");
        estudianteDto.setCiudad("Bogota");
        estudianteDto.setEdad(25);
        estudianteDto.setZonaHoraria("America/Bogota");
    }

    @Test
    void crearEstudianteDeberiaRetornarEstudianteCreado() {
        // Configuración del comportamiento esperado del servicio
        when(estudianteService.crearEstudiante(estudianteDto)).thenReturn(estudianteDto);

        // Ejecución del método a probar
        ResponseEntity<EstudianteDto> response = estudianteController.crearEstudiante(estudianteDto);

        // Aserciones para verificar el resultado esperado
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(estudianteDto, response.getBody());
    }
}
