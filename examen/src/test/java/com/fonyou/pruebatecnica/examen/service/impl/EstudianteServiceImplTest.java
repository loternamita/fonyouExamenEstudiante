package com.fonyou.pruebatecnica.examen.service.impl;

import com.fonyou.pruebatecnica.examen.dto.EstudianteDto;
import com.fonyou.pruebatecnica.examen.exception.CustomServiceException;
import com.fonyou.pruebatecnica.examen.model.Estudiante;
import com.fonyou.pruebatecnica.examen.repository.EstudianteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class EstudianteServiceImplTest {

    @Mock
    private EstudianteRepository estudianteRepository;
    @Mock
    private ModelMapperServiceImpl modelMapperService;
    @InjectMocks
    private EstudianteServiceImpl estudianteService;
    private EstudianteDto estudianteDto;
    private Estudiante estudianteEntity;

    @BeforeEach
    public void setUp(){
        estudianteDto = new EstudianteDto();
        estudianteDto.setNombre("Andres Felipe");
        estudianteDto.setZonaHoraria("America/Bogota");
        estudianteDto.setEdad(25);
        estudianteDto.setCiudad("Bogota");

        estudianteEntity = new Estudiante();
        estudianteEntity.setNombre(estudianteDto.getNombre());
        estudianteEntity.setZonaHoraria(estudianteDto.getZonaHoraria());
        estudianteEntity.setEdad(estudianteDto.getEdad());
        estudianteEntity.setCiudad(estudianteDto.getCiudad());
    }

    @Test
    public void cuandoCreaEstudianteConZonaHorariaValida_deberiaRetornarEstudianteDto() {

        when(modelMapperService.convertToEntity(estudianteDto, Estudiante.class)).thenReturn(estudianteEntity);
        when(estudianteRepository.save(any(Estudiante.class))).thenReturn(estudianteEntity);
        when(modelMapperService.convertToDto(estudianteEntity, EstudianteDto.class)).thenReturn(estudianteDto);

        EstudianteDto resultado = estudianteService.crearEstudiante(estudianteDto);

        assertNotNull(resultado);
        assertEquals(estudianteDto.getNombre(), resultado.getNombre());
        verify(estudianteRepository, times(1)).save(any(Estudiante.class));

    }

    @Test
    public void cuandoCreaEstudianteConZonaHorariaInvalida_deberiaLanzarExcepcion() {
        estudianteDto.setZonaHoraria("Invalid/Zone");

        Exception exception = assertThrows(CustomServiceException.class, () -> {
            estudianteService.crearEstudiante(estudianteDto);
        });

        String expectedMessage = "La zona horaria proporcionada no es válida.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(estudianteRepository, never()).save(any(Estudiante.class));
    }
}
