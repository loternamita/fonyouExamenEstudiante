package com.fonyou.pruebatecnica.examen.repository;

import com.fonyou.pruebatecnica.examen.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {
    List<Estudiante> findAllByZonaHoraria(String zonaHoraria);
}
