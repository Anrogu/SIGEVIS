package com.proyecto.SsYPp.Repository;

import com.proyecto.SsYPp.Entity.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarreraRepository extends JpaRepository<Carrera,Integer> {

    List<Carrera> findByEscuela_Idescuela(Integer idescuela);

}