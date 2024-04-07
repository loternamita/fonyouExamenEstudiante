package com.fonyou.pruebatecnica.examen.service.impl;

import com.fonyou.pruebatecnica.examen.dto.EstudianteDto;
import com.fonyou.pruebatecnica.examen.exception.CustomServiceException;
import com.fonyou.pruebatecnica.examen.model.Estudiante;
import com.fonyou.pruebatecnica.examen.repository.EstudianteRepository;
import com.fonyou.pruebatecnica.examen.service.EstudianteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.time.ZoneId;
import java.time.zone.ZoneRulesException;

@Service
@Validated
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final ModelMapperServiceImpl mapperService;
    private static final Logger log = LogManager.getLogger(EstudianteServiceImpl.class);

    public EstudianteServiceImpl(EstudianteRepository estudianteRepository,
                                 ModelMapperServiceImpl mapperService) {
        this.estudianteRepository = estudianteRepository;
        this.mapperService = mapperService;
    }

    @Override
    public EstudianteDto crearEstudiante(EstudianteDto estudianteDto) {

        log.info("Validando zona horaria del estudiante");
        if (!esZonaHorariaValida(estudianteDto.getZonaHoraria())) throw new CustomServiceException("La zona horaria proporcionada no es válida.");

        log.info("Convirtio de dto a entidad");
        Estudiante estudianteEntity = mapperService.convertToEntity(estudianteDto, Estudiante.class);

        log.info("Guardo datos en la BD");
        estudianteEntity = estudianteRepository.save(estudianteEntity);

        return mapperService.convertToDto(estudianteEntity, EstudianteDto.class);
    }

    public boolean esZonaHorariaValida(String zonaHoraria){
        if (!StringUtils.hasText(zonaHoraria)) return false;

        try {
            ZoneId.of(zonaHoraria);
            return true;
        }catch (ZoneRulesException e){
            return false;
        }
    }
}
