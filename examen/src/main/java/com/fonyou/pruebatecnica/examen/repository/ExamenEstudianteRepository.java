package com.fonyou.pruebatecnica.examen.repository;

import com.fonyou.pruebatecnica.examen.model.Estudiante;
import com.fonyou.pruebatecnica.examen.model.Examen;
import com.fonyou.pruebatecnica.examen.model.ExamenEstudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamenEstudianteRepository extends JpaRepository<ExamenEstudiante, Integer> {
    ExamenEstudiante findByEstudianteAndExamen(Estudiante estudiante, Examen examen);
}
