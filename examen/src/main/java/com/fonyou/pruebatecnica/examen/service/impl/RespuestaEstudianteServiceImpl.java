package com.fonyou.pruebatecnica.examen.service.impl;

import com.fonyou.pruebatecnica.examen.dto.CalificacionExamenDto;
import com.fonyou.pruebatecnica.examen.dto.DetallesPreguntasDto;
import com.fonyou.pruebatecnica.examen.dto.RespuestaEstudianteDto;
import com.fonyou.pruebatecnica.examen.exception.CustomServiceException;
import com.fonyou.pruebatecnica.examen.model.*;
import com.fonyou.pruebatecnica.examen.repository.*;
import com.fonyou.pruebatecnica.examen.service.RespuestaEstudianteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RespuestaEstudianteServiceImpl implements RespuestaEstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final PreguntaRepository preguntaRepository;
    private final RespuestaEstudianteRepository respuestaEstudianteRepository;
    private final ExamenEstudianteRepository examenEstudianteRepository;
    private static final Logger log = LogManager.getLogger(RespuestaEstudianteServiceImpl.class);

    public RespuestaEstudianteServiceImpl(EstudianteRepository estudianteRepository,
                                          PreguntaRepository preguntaRepository,
                                          RespuestaEstudianteRepository respuestaEstudianteRepository,
                                          ExamenEstudianteRepository examenEstudianteRepository) {
        this.estudianteRepository = estudianteRepository;
        this.preguntaRepository = preguntaRepository;
        this.respuestaEstudianteRepository = respuestaEstudianteRepository;
        this.examenEstudianteRepository = examenEstudianteRepository;
    }

    @Override
    @Transactional
    public CalificacionExamenDto recopilarYCalificarRespuestas(RespuestaEstudianteDto respuestaEstudianteDto) {

        CalificacionExamenDto calificacionDto = new CalificacionExamenDto();
        int puntuacionTotal = 0;
        int totalPuntosExamen = 0;
        ExamenEstudiante examenEstudiante = null;
        List<DetallesPreguntasDto> detallesPreguntas = new ArrayList<>();

        log.info("Buscar el estudiante");
        Estudiante estudianteEntity = buscarEstudiante(Integer.parseInt(String.valueOf(respuestaEstudianteDto.getIdEstudiante())));

        log.info("Mapeamos las respuestas a ese estudiante");
        for (Map.Entry<Long, Character> respuestaEntry: respuestaEstudianteDto.getRespuestas().entrySet()){

            log.info("Buscamos la pregunta");
            Pregunta preguntaEntity = buscarPregunta(respuestaEntry.getKey());

            log.info("Validamos cuanto fue su puntaje y obtenemos los detalles del examen");
            int puntajeObtenido = preguntaEntity.getOpcionCorrecta().equals(respuestaEntry.getValue()) ? preguntaEntity.getPuntaje() : 0;
            puntuacionTotal += puntajeObtenido;
            detallesPreguntas.add(new DetallesPreguntasDto(preguntaEntity.getId(), preguntaEntity.getEnunciado() , respuestaEntry.getValue(), puntajeObtenido, preguntaEntity.getOpcionCorrecta()));

            log.info("Guardamos la respuesta del estudiante");
            RespuestaEstudianteId respuestaEstudianteId = new RespuestaEstudianteId(respuestaEstudianteDto.getIdEstudiante(), respuestaEntry.getKey());
            guardarRespuestaEstudiante(respuestaEstudianteId,estudianteEntity,preguntaEntity,respuestaEntry.getValue());

            log.info("Buscamos el examen asignado a ese estudiante");
            if (examenEstudiante == null) {
                examenEstudiante = buscarEstudianteXExamen(estudianteEntity, preguntaEntity.getExamen());
                totalPuntosExamen = examenEstudiante.getExamen().getTotalPuntos();
            }
        }

        log.info("Asignamos la calificacion");
        if (examenEstudiante != null) {
            examenEstudiante.setCalificacion(puntuacionTotal);
            examenEstudianteRepository.save(examenEstudiante);
        }

        calificacionDto.setIdEstudiante(estudianteEntity.getId());
        calificacionDto.setCalificacion(puntuacionTotal);
        calificacionDto.setDetallesPreguntas(detallesPreguntas);
        calificacionDto.setTotalPuntosExamen(totalPuntosExamen);

        return calificacionDto;
    }

    private Pregunta buscarPregunta(Long idPregunta){
        return preguntaRepository.findById(Math.toIntExact(idPregunta))
                .orElseThrow(() -> new CustomServiceException("Examen o Pregunta no encontrada"));
    }

    private Estudiante buscarEstudiante(Integer idEstudiante){
        return estudianteRepository.findById(idEstudiante)
                .orElseThrow( () ->  new CustomServiceException("Estudiante no encontrado"));
    }

    private ExamenEstudiante buscarEstudianteXExamen(Estudiante estudianteEntity, Examen examenEntity){
        return examenEstudianteRepository.findByEstudianteAndExamen(estudianteEntity, examenEntity);
    }

    private void guardarRespuestaEstudiante(RespuestaEstudianteId respuestaEstudianteId, Estudiante estudiante, Pregunta pregunta, Character opcionElegida){
        RespuestaEstudiante respuestaEstudiante = new RespuestaEstudiante();
        respuestaEstudiante.setId(respuestaEstudianteId);
        respuestaEstudiante.setEstudiante(estudiante);
        respuestaEstudiante.setPregunta(pregunta);
        respuestaEstudiante.setOpcionElegida(opcionElegida);

        respuestaEstudianteRepository.save(respuestaEstudiante);
    }
}
