package com.fonyou.pruebatecnica.examen.repository;

import com.fonyou.pruebatecnica.examen.model.RespuestaEstudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespuestaEstudianteRepository extends JpaRepository<RespuestaEstudiante, Integer> {
}
