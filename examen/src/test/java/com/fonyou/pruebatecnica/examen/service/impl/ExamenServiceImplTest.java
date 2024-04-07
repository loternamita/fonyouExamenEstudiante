package com.fonyou.pruebatecnica.examen.service.impl;

import com.fonyou.pruebatecnica.examen.dto.ExamenDto;
import com.fonyou.pruebatecnica.examen.dto.OpcionDto;
import com.fonyou.pruebatecnica.examen.dto.PreguntaDto;
import com.fonyou.pruebatecnica.examen.exception.CustomServiceException;
import com.fonyou.pruebatecnica.examen.model.Examen;
import com.fonyou.pruebatecnica.examen.model.Opcion;
import com.fonyou.pruebatecnica.examen.model.Pregunta;
import com.fonyou.pruebatecnica.examen.repository.ExamenRepository;
import com.fonyou.pruebatecnica.examen.repository.OpcionRepository;
import com.fonyou.pruebatecnica.examen.repository.PreguntaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Description;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class ExamenServiceImplTest {

    @Mock
    private ExamenRepository examenRepository;
    @Mock
    private PreguntaRepository preguntaRepository;
    @Mock
    private OpcionRepository opcionRepository;
    @Mock
    private ModelMapperServiceImpl mapperService;
    @InjectMocks
    private ExamenServiceImpl examenService;

    private ExamenDto examenDto;

    @BeforeEach
    public void setUp() {
        examenDto = new ExamenDto();
        examenDto.setTotalPuntos(100);
        PreguntaDto preguntaDto = getPreguntaDto();
        examenDto.setPregunta(Set.of(preguntaDto));
    }

    private static PreguntaDto getPreguntaDto() {

        PreguntaDto preguntaDto = new PreguntaDto();
        preguntaDto.setEnunciado("¿Cuál es la capital de Francia?");
        preguntaDto.setOpcionCorrecta('A');
        preguntaDto.setPuntaje(100);

        // Crear opciones
        OpcionDto opcion1 = new OpcionDto();
        opcion1.setTexto("Paris");
        opcion1.setOpcion('A');

        OpcionDto opcion2 = new OpcionDto();
        opcion2.setTexto("Londres");
        opcion2.setOpcion('B');

        OpcionDto opcion3 = new OpcionDto();
        opcion3.setTexto("Roma");
        opcion3.setOpcion('C');

        OpcionDto opcion4 = new OpcionDto();
        opcion4.setTexto("Madrid");
        opcion4.setOpcion('D');

        // Usar un conjunto mutable para agregar opciones
        Set<OpcionDto> opcionesDto = new HashSet<>();
        opcionesDto.add(opcion1);
        opcionesDto.add(opcion2);
        opcionesDto.add(opcion3);
        opcionesDto.add(opcion4);

        preguntaDto.setOpciones(opcionesDto);
        return preguntaDto;
    }

    @Test
    @Description("Crea el examen con todas las preguntas y opciones")
    public void cuandoCrearExamenConPuntajeTotal100_deberiaRetornarExamenDto() {

        Examen examenEntity = new Examen();

        when(preguntaRepository.save(any(Pregunta.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(opcionRepository.save(any(Opcion.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(mapperService.convertToEntity(any(ExamenDto.class), eq(Examen.class))).thenReturn(examenEntity);
        when(mapperService.convertToEntity(any(PreguntaDto.class), eq(Pregunta.class))).thenReturn(new Pregunta());
        when(mapperService.convertToEntity(any(OpcionDto.class), eq(Opcion.class))).thenReturn(new Opcion());

        when(examenRepository.save(any(Examen.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ExamenDto result = examenService.crearExamen(examenDto);

        assertNotNull(result);
        assertEquals(100, result.getTotalPuntos());

        verify(examenRepository).save(any(Examen.class));
        verify(preguntaRepository, times(1)).save(any(Pregunta.class));
        verify(opcionRepository, times(4)).save(any(Opcion.class));
    }

    @Test
    @Description("Configura el DTO de examen con preguntas que sumen menos de 100 puntos.")
    void cuandoLaSumaDePuntosNoEs100_deberiaLanzarExcepcion() {
        examenDto.getPregunta().forEach(preguntaDto -> preguntaDto.setPuntaje(25));
        assertThrows(CustomServiceException.class, () -> examenService.crearExamen(examenDto), "La suma de los puntos de todas las preguntas debe ser 100");
    }

    @Test
    @Description("Configurar una pregunta sin opciones")
    void cuandoUnaPreguntaNoTieneOpciones_deberiaManejarAdecuadamente() {
        PreguntaDto preguntaSinOpciones = new PreguntaDto();
        preguntaSinOpciones.setEnunciado("Cual es la capital de colombia?");
        preguntaSinOpciones.setPuntaje(50);
        examenDto.setPregunta(Set.of(preguntaSinOpciones));

        assertThrows(CustomServiceException.class, () -> examenService.crearExamen(examenDto),
                "No se encontro opciones para la pregunta");
    }

    @Test
    @Description("Configurar el DTO de examen sin preguntas")
    void cuandoCrearExamenSinPreguntas_deberiaLanzarExcepcion() {
        examenDto.setPregunta(Collections.emptySet());
        assertThrows(CustomServiceException.class, () -> examenService.crearExamen(examenDto),
                "Debería lanzar una excepción cuando se intente crear un examen sin preguntas.");
    }

}
