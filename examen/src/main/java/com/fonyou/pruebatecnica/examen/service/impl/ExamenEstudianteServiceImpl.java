package com.fonyou.pruebatecnica.examen.service.impl;

import com.fonyou.pruebatecnica.examen.dto.ExamenEstudianteDto;
import com.fonyou.pruebatecnica.examen.exception.CustomServiceException;
import com.fonyou.pruebatecnica.examen.model.Estudiante;
import com.fonyou.pruebatecnica.examen.model.Examen;
import com.fonyou.pruebatecnica.examen.model.ExamenEstudiante;
import com.fonyou.pruebatecnica.examen.repository.EstudianteRepository;
import com.fonyou.pruebatecnica.examen.repository.ExamenEstudianteRepository;
import com.fonyou.pruebatecnica.examen.repository.ExamenRepository;
import com.fonyou.pruebatecnica.examen.service.ExamenEstudianteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ExamenEstudianteServiceImpl implements ExamenEstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final ExamenRepository examenRepository;
    private final ExamenEstudianteRepository examenEstudianteRepository;
    private static final Logger log = LogManager.getLogger(EstudianteServiceImpl.class);

    public ExamenEstudianteServiceImpl(EstudianteRepository estudianteRepository,
                                       ExamenRepository examenRepository,
                                       ExamenEstudianteRepository examenEstudianteRepository) {
        this.estudianteRepository = estudianteRepository;
        this.examenRepository = examenRepository;
        this.examenEstudianteRepository = examenEstudianteRepository;
    }

    @Override
    @Transactional
    public ExamenEstudianteDto asignarExamenAEstudiante(Integer idExamen, Map<String, LocalDateTime> fechasPorZonaHoraria) {

        ExamenEstudianteDto response = new ExamenEstudianteDto();

        log.info("Encontrar examen");
        Examen examenEntity = examenRepository.findById(idExamen)
                .orElseThrow(() -> new CustomServiceException("Examen no encontrado"));

        log.info("Buscar todos los estudiantes por zona horaria");
        fechasPorZonaHoraria.forEach((zonaHoraria, fechaExamen) -> {
            List<Estudiante> estudiantesEnZona = estudianteRepository.findAllByZonaHoraria(zonaHoraria);

            if (estudiantesEnZona.isEmpty()) {
                response.agregarMensaje("No se asignaron exámenes en la zona horaria: " + zonaHoraria + " ya que no hay estudiantes.");
            } else {
                estudiantesEnZona.forEach(estudiante -> {
                    asignarExamen(estudiante, examenEntity, fechaExamen);
                    response.agregarMensaje("Examen asignado a " + estudiante.getNombre() + " en la zona horaria " + zonaHoraria);
                });
            }
        });

        return response;
    }

    private void asignarExamen(Estudiante estudiante, Examen examen, LocalDateTime fechaExamenBogota) {

        log.info("Verificar si el examen ya está asignado al estudiante.");
        ExamenEstudiante examenEstudianteExistente = examenEstudianteRepository
                .findByEstudianteAndExamen(estudiante, examen);

        if (examenEstudianteExistente != null) throw new CustomServiceException("El examen ya está asignado al estudiante: " + estudiante.getNombre());

        log.info("Recorrer todos los estudiantes y asignar el examen y guardar.");
        LocalDateTime fechaExamenEstudiante = convertirFechaExamenXZonaHoraria(fechaExamenBogota, estudiante.getZonaHoraria());
        ExamenEstudiante examenEstudiante = new ExamenEstudiante();
        examenEstudiante.setEstudiante(estudiante);
        examenEstudiante.setExamen(examen);
        examenEstudiante.setEstado("Asignado");
        examenEstudiante.setCalificacion(0);
        examenEstudiante.setFechaExamenZonaHoraria(fechaExamenEstudiante);
        examenEstudianteRepository.save(examenEstudiante);
    }

    private LocalDateTime convertirFechaExamenXZonaHoraria(LocalDateTime fechaExamen, String zonaHorariaEstudiante){
        log.info("Convertir la fecha del examen a la zona horaria");
        try {
            ZonedDateTime fechaExamenZona = fechaExamen.atZone(ZoneId.of("America/Bogota"));
            ZonedDateTime fechaExamenZonEstudiante = fechaExamenZona.withZoneSameInstant(ZoneId.of(zonaHorariaEstudiante));
            return fechaExamenZonEstudiante.toLocalDateTime();
        }catch (DateTimeException e){
            throw new CustomServiceException("Zona horaria inválida: " + zonaHorariaEstudiante);
        }
    }
}
