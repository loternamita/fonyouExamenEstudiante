package com.fonyou.pruebatecnica.examen.repository;

import com.fonyou.pruebatecnica.examen.model.Opcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpcionRepository extends JpaRepository<Opcion, Integer> {
}
