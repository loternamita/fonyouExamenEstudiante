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
import com.fonyou.pruebatecnica.examen.service.ExamenService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExamenServiceImpl implements ExamenService {

    private final ExamenRepository examenRepository;
    private final PreguntaRepository preguntaRepository;
    private final OpcionRepository opcionRepository;
    private final ModelMapperServiceImpl mapperService;
    private static final Logger log = LogManager.getLogger(EstudianteServiceImpl.class);

    //Inyeccion de constructor para todas las dependencias
    public ExamenServiceImpl(ExamenRepository examenRepository,
                             PreguntaRepository preguntaRepository,
                             OpcionRepository opcionRepository,
                             ModelMapperServiceImpl mapperService) {
        this.examenRepository = examenRepository;
        this.preguntaRepository = preguntaRepository;
        this.opcionRepository = opcionRepository;
        this.mapperService = mapperService;
    }

    @Override
    @Transactional
    public ExamenDto crearExamen(ExamenDto examenDto) {

        log.info("Validar que el total de puntos de todas las preguntas da un total de 100");
        validarPuntosTotales(examenDto);

        log.info("Convertir el modelo a un dto");
        Examen examenEntity = mapperService.convertToEntity(examenDto, Examen.class);

        log.info("Guardar examen");
        examenEntity = examenRepository.save(examenEntity);

        log.info("Construir el set de preguntas y opciones para posteriormente enviar al response en el examen");
        Examen examenPre = examenEntity;
        Set<PreguntaDto> preguntasProcesadas = examenDto.getPregunta().stream()
                .map(preguntaDto -> procesarPreguntaDto(preguntaDto, examenPre))
                .collect(Collectors.toSet());

        examenDto.setPregunta(preguntasProcesadas);
        return examenDto;
    }

    private void validarPuntosTotales(ExamenDto examenDto) {
        int totalPuntos = examenDto.getPregunta().stream()
                .mapToInt(PreguntaDto::getPuntaje)
                .sum();
        if (totalPuntos != 100) throw new CustomServiceException("La suma de los puntos de todas las preguntas debe ser 100, actual: " + totalPuntos);
    }

    private PreguntaDto procesarPreguntaDto(PreguntaDto preguntaDto, Examen examen) {

        log.info("Convertir dto pregunta a modelo");
        Pregunta pregunta = mapperService.convertToEntity(preguntaDto, Pregunta.class);
        pregunta.setExamen(examen);

        log.info("Guardar pregunta");
        pregunta = preguntaRepository.save(pregunta);
        Pregunta preguntaPre = pregunta;

        log.info("Procesar opciones de pregunta");
        Set<OpcionDto> opcionesProcesadas = preguntaDto.getOpciones().stream()
                .map(opcionDto -> procesarOpcionDto(opcionDto, preguntaPre))
                .collect(Collectors.toSet());
        preguntaDto.setOpciones(opcionesProcesadas);
        return preguntaDto;
    }

    private OpcionDto procesarOpcionDto(OpcionDto opcionDto, Pregunta pregunta) {

        log.info("Convertir dto opcion a modelo");
        Opcion opcion = mapperService.convertToEntity(opcionDto, Opcion.class);
        opcion.setPregunta(pregunta);

        log.info("Guardar opcion");
        opcionRepository.save(opcion);
        return mapperService.convertToDto(opcion, OpcionDto.class);
    }
}
