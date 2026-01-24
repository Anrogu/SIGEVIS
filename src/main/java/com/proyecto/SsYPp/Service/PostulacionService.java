package com.proyecto.SsYPp.Service;

import com.proyecto.SsYPp.Dto.PostulacionCoordinadorDetalleDto;
import com.proyecto.SsYPp.Dto.PostulacionCoordinadorRowDto;
import com.proyecto.SsYPp.Dto.PostulacionDto;

import java.util.List;

public interface PostulacionService {

    PostulacionDto create(PostulacionDto postulacion);

    List<PostulacionDto> getAll();

    PostulacionDto get(Long id);

    void delete(Long id);

    PostulacionDto update(PostulacionDto postulacion);

    // ✅ Prestador: crear postulación
    PostulacionDto postular(Long vacanteId, String comentarios, String authName);

    // ✅ Coordinador: listado para tabla
    List<PostulacionCoordinadorRowDto> misPostulacionesCoordinador(String authName);

    // ✅ Coordinador: detalle
    PostulacionCoordinadorDetalleDto detalleCoordinador(Long postulacionId, String authName);

    // ✅ Coordinador: cambiar estatus (Individual)
    void cambiarEstatus(Long postulacionId, Long estatusId, String authName);

    // ✅ Coordinador: Gestión Masiva (SOLO LA FIRMA AQUÍ)
    void gestionarCandidatos(List<Long> ids, Long statusId, String mensajeExtra);
}