package com.proyecto.SsYPp.Service;

import com.proyecto.SsYPp.Dto.PostulacionCoordinadorDetalleDto;
import com.proyecto.SsYPp.Dto.PostulacionCoordinadorRowDto;
import com.proyecto.SsYPp.Dto.PostulacionDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostulacionService {

    PostulacionDto create(PostulacionDto postulacion);

    List<PostulacionDto> getAll();

    PostulacionDto get(Long id);

    void delete(Long id);

    PostulacionDto update(PostulacionDto postulacion);

    // ✅ Prestador: crear postulación
    PostulacionDto postular(Long vacanteId, String comentarios, String authName);

    // ✅ Coordinador: listado para tabla (YA con nombres)
    List<PostulacionCoordinadorRowDto> misPostulacionesCoordinador(String authName);

    // ✅ Coordinador: detalle (comprobación)  [lo implementamos después]
    PostulacionCoordinadorDetalleDto detalleCoordinador(Long postulacionId, String authName);

    // ✅ Coordinador: cambiar estatus (Nuevo->Aceptar/Rechazar) [lo implementamos después]
    void cambiarEstatus(Long postulacionId, Long estatusId, String authName);
}
