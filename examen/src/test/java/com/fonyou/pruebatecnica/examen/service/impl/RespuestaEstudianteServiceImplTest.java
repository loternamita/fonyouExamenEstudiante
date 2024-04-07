package com.fonyou.pruebatecnica.examen.service.impl;

import com.fonyou.pruebatecnica.examen.dto.CalificacionExamenDto;
import com.fonyou.pruebatecnica.examen.dto.RespuestaEstudianteDto;
import com.fonyou.pruebatecnica.examen.exception.CustomServiceException;
import com.fonyou.pruebatecnica.examen.model.*;
import com.fonyou.pruebatecnica.examen.repository.EstudianteRepository;
import com.fonyou.pruebatecnica.examen.repository.ExamenEstudianteRepository;
import com.fonyou.pruebatecnica.examen.repository.PreguntaRepository;
import com.fonyou.pruebatecnica.examen.repository.RespuestaEstudianteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.HashMap;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class RespuestaEstudianteServiceImplTest {

    @Mock
    private EstudianteRepository estudianteRepository;
    @Mock
    private PreguntaRepository preguntaRepository;
    @Mock
    private RespuestaEstudianteRepository respuestaEstudianteRepository;
    @Mock
    private ExamenEstudianteRepository examenEstudianteRepository;

    @InjectMocks
    private RespuestaEstudianteServiceImpl respuestaEstudianteService;
    private Estudiante estudianteMock;
    private Pregunta preguntaMock;
    private ExamenEstudiante examenEstudianteMock;
    private Examen examenMock;
    private RespuestaEstudianteDto respuestaEstudianteDto;

    @BeforeEach
    public void setUp() {
        estudianteMock = new Estudiante();
        estudianteMock.setId(1L);

        examenMock = new Examen();
        examenMock.setId(1L);
        examenMock.setTotalPuntos(100);

        preguntaMock = new Pregunta();
        preguntaMock.setId(1L);
        preguntaMock.setOpcionCorrecta('A');
        preguntaMock.setPuntaje(10);
        preguntaMock.setExamen(examenMock);

        examenEstudianteMock = new ExamenEstudiante();
        examenEstudianteMock.setEstudiante(estudianteMock);
        examenEstudianteMock.setExamen(examenMock);
        examenEstudianteMock.setCalificacion(0);

        respuestaEstudianteDto = new RespuestaEstudianteDto();
        respuestaEstudianteDto.setIdEstudiante(estudianteMock.getId());
        respuestaEstudianteDto.setRespuestas(new HashMap<>());
        respuestaEstudianteDto.getRespuestas().put(preguntaMock.getId(), 'A');
    }

    @Test
    public void recopilarYCalificarRespuestas_DeberiaCalificarCorrectamente() {

        when(estudianteRepository.findById(1)).thenReturn(Optional.of(estudianteMock));
        when(preguntaRepository.findById(1)).thenReturn(Optional.of(preguntaMock));
        when(examenEstudianteRepository.findByEstudianteAndExamen(estudianteMock, examenMock)).thenReturn(examenEstudianteMock);

        CalificacionExamenDto result = respuestaEstudianteService.recopilarYCalificarRespuestas(respuestaEstudianteDto);

        assertNotNull(result);
        assertNotNull(result.getDetallesPreguntas());
        assertFalse(result.getDetallesPreguntas().isEmpty());
        assertEquals(10, result.getCalificacion());
        assertEquals(100, result.getTotalPuntosExamen());

        verify(examenEstudianteRepository).findByEstudianteAndExamen(estudianteMock, examenMock);
        verify(respuestaEstudianteRepository).save(any(RespuestaEstudiante.class));
    }

    @Test
    void recopilarYCalificarRespuestas_EstudianteNoEncontrado() {

        when(estudianteRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CustomServiceException.class,
                () -> respuestaEstudianteService.recopilarYCalificarRespuestas(respuestaEstudianteDto),
                "Estudiante no encontrado");
    }

}
