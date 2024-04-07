package com.fonyou.pruebatecnica.examen.repository;

import com.fonyou.pruebatecnica.examen.model.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta, Integer> {
}
