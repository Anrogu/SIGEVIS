package com.proyecto.SsYPp.Repository;

import com.proyecto.SsYPp.Entity.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticiaRepository extends JpaRepository<Noticia, Long> {

    // ✅ Solo noticias con estatus "Publicada" (ignora mayúsculas/minúsculas)
    // ✅ Ordena de más reciente a más antigua
    List<Noticia> findByEstatusIgnoreCaseOrderByFechaPublicacionDesc(String estatus);
}
