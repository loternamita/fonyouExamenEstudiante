package com.fonyou.pruebatecnica.examen.service.impl;

import com.fonyou.pruebatecnica.examen.dto.*;
import com.fonyou.pruebatecnica.examen.exception.CustomServiceException;
import com.fonyou.pruebatecnica.examen.model.*;
import com.fonyou.pruebatecnica.examen.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class ExamenEstudianteServiceImplTest {
    @Mock
    private EstudianteRepository estudianteRepository;
    @Mock
    private ExamenRepository examenRepository;
    @Mock
    private ExamenEstudianteRepository examenEstudianteRepository;
    @InjectMocks
    private ExamenEstudianteServiceImpl examenEstudianteService;

    private Examen examenMock;
    private Estudiante estudianteMock;
    private Map<String, LocalDateTime> fechasPorZonaHoraria;

    @BeforeEach
    public void setUp() {

        examenMock = new Examen();
        examenMock.setId(1L);

        estudianteMock = new Estudiante();
        estudianteMock.setId(1L);
        estudianteMock.setNombre("Estudiante Test");
        estudianteMock.setZonaHoraria("America/New_York");

        LocalDateTime fechaExamenMock = LocalDateTime.of(2024, 4, 15, 10, 0);

        fechasPorZonaHoraria = new HashMap<>();
        fechasPorZonaHoraria.put(estudianteMock.getZonaHoraria(), fechaExamenMock);
    }

    @Test
    public void asignarExamenAEstudianteExito(){

        when(examenRepository.findById(1)).thenReturn(Optional.of(examenMock));
        when(estudianteRepository.findAllByZonaHoraria("America/New_York")).thenReturn(Collections.singletonList(estudianteMock));
        when(examenEstudianteRepository.findByEstudianteAndExamen(estudianteMock, examenMock)).thenReturn(null);
        when(examenEstudianteRepository.save(any(ExamenEstudiante.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ExamenEstudianteDto resultado = examenEstudianteService.asignarExamenAEstudiante(Math.toIntExact(examenMock.getId()), fechasPorZonaHoraria);

        assertNotNull(resultado);
        verify(examenEstudianteRepository).save(any(ExamenEstudiante.class));
    }

    @Test
    public void cuandoElExamenNoSeEncuentraDeberiaLanzarExcepcion() {
        Integer idExamenInexistente = 999;
        when(examenRepository.findById(idExamenInexistente)).thenReturn(Optional.empty());

        assertThrows(CustomServiceException.class,
                () -> examenEstudianteService.asignarExamenAEstudiante(idExamenInexistente, fechasPorZonaHoraria),
                "El examen no existe");
    }

    @Test
    public void cuandoNoHayEstudiantesPorZonaHorariaDeberiaAgregarMensajeCorrespondiente() {
        String zonaHorariaSinEstudiantes = "Antarctica/Troll";
        Map<String, LocalDateTime> fechasZonaHorariaVacia = Map.of(zonaHorariaSinEstudiantes, LocalDateTime.now());
        when(examenRepository.findById(1)).thenReturn(Optional.of(examenMock));
        when(estudianteRepository.findAllByZonaHoraria(zonaHorariaSinEstudiantes)).thenReturn(Collections.emptyList());

        ExamenEstudianteDto resultado = examenEstudianteService.asignarExamenAEstudiante(Math.toIntExact(examenMock.getId()), fechasZonaHorariaVacia);

        assertNotNull(resultado);
        assertTrue(resultado.getMensajes().stream().anyMatch(mensaje -> mensaje.contains("No se asignaron exámenes en la zona horaria")));
    }

    @Test
    public void cuandoLaZonaHorariaEsInvalidaDeberiaLanzarExcepcion() {
        String zonaHorariaInvalida = "Invalid/Zone";
        Map<String, LocalDateTime> fechasConZonaHorariaInvalida = Map.of(zonaHorariaInvalida, LocalDateTime.now());
        when(examenRepository.findById(1)).thenReturn(Optional.of(examenMock));
        when(estudianteRepository.findAllByZonaHoraria(zonaHorariaInvalida)).thenThrow(new CustomServiceException("Zona horaria inválida"));

        assertThrows(CustomServiceException.class,
                () -> examenEstudianteService.asignarExamenAEstudiante(Math.toIntExact(examenMock.getId()), fechasConZonaHorariaInvalida),
                "Zona horaria inválida");
    }

}
