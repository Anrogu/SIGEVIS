package com.proyecto.SsYPp.Service.Impl;

import com.proyecto.SsYPp.Dto.PostulacionCoordinadorDetalleDto;
import com.proyecto.SsYPp.Dto.PostulacionCoordinadorRowDto;
import com.proyecto.SsYPp.Dto.PostulacionDto;
import com.proyecto.SsYPp.Entity.Postulacion;
import com.proyecto.SsYPp.Entity.StatusPostulacion;
import com.proyecto.SsYPp.Entity.Usuario;
import com.proyecto.SsYPp.Entity.Vacante;
import com.proyecto.SsYPp.Repository.PostulacionRepository;
import com.proyecto.SsYPp.Repository.StatusPostulacionRepository;
import com.proyecto.SsYPp.Repository.UsuarioRepository;
import com.proyecto.SsYPp.Repository.VacanteRepository;
import com.proyecto.SsYPp.Service.PostulacionService;
import com.proyecto.SsYPp.Service.AsignacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetTime;
import java.util.List;
import java.util.stream.Collectors;

import com.proyecto.SsYPp.Entity.Actividad;
import com.proyecto.SsYPp.Repository.ActividadRepository;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class PostulacionServiceImpl implements PostulacionService {

    @Autowired
    private PostulacionRepository postulacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private StatusPostulacionRepository statusPostulacionRepository;

    @Autowired
    private VacanteRepository vacanteRepository;

    @Autowired
    private AsignacionService asignacionService;

    @Autowired
    private ActividadRepository actividadRepository;

    @Override
    public PostulacionDto create(PostulacionDto postulacion) {
        Postulacion entidad = convertirDTOAEntidad(postulacion);
        Postulacion guardada = postulacionRepository.save(entidad);
        return convertirEntidadADTO(guardada);
    }

    @Override
    public List<PostulacionDto> getAll() {
        return postulacionRepository.findAll()
                .stream()
                .map(this::convertirEntidadADTO)
                .collect(Collectors.toList());
    }

    @Override
    public PostulacionDto get(Long id) {
        Postulacion postulacion = postulacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("postulacion no encontrada"));
        return convertirEntidadADTO(postulacion);
    }

    @Override
    public void delete(Long id) {
        postulacionRepository.deleteById(id);
    }

    @Override
    public PostulacionDto update(PostulacionDto postulacion) {
        Postulacion entidad = convertirDTOAEntidad(postulacion);
        Postulacion actualizada = postulacionRepository.save(entidad);
        return convertirEntidadADTO(actualizada);
    }

    // ✅ Prestador -> crear postulación
    @Override
    public PostulacionDto postular(Long vacanteId, String comentarios, String authName) {

        Usuario usuario = usuarioRepository.findByEmail(authName);
        if (usuario == null) {
            throw new RuntimeException("Usuario autenticado no encontrado: " + authName);
        }

        Vacante vacante = vacanteRepository.findById(vacanteId)
                .orElseThrow(() -> new RuntimeException("Vacante no encontrada"));

        boolean yaExiste = postulacionRepository
                .existsByUsuariosIdusuario_IdAndVacanteIdvacante_Id(usuario.getId(), vacante.getId());

        if (yaExiste) {
            throw new RuntimeException("Ya existe una postulación para esta vacante");
        }

        StatusPostulacion pendiente = statusPostulacionRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Estatus PENDIENTE no encontrado"));

        Postulacion postulacion = new Postulacion();
        postulacion.setUsuariosIdusuario(usuario);
        postulacion.setVacanteIdvacante(vacante);
        postulacion.setEstatusIdestatus(pendiente);
        postulacion.setFechaPostulacion(OffsetTime.now());
        postulacion.setComentarios(comentarios);

        Postulacion guardada = postulacionRepository.save(postulacion);
        return convertirEntidadADTO(guardada);
    }

    // ✅ Coordinador -> listado para tabla (YA con nombres)
    @Override
    public List<PostulacionCoordinadorRowDto> misPostulacionesCoordinador(String authName) {

        Usuario coordinador = usuarioRepository.findByEmail(authName);
        if (coordinador == null) {
            throw new RuntimeException("Coordinador no encontrado: " + authName);
        }

        if (coordinador.getArea() == null || coordinador.getArea().getId() == null) {
            throw new RuntimeException("El coordinador no tiene área asignada");
        }

        Long areaId = coordinador.getArea().getId();
        return postulacionRepository.findRowsByArea(areaId);
    }

    // ✅ Coordinador -> detalle (comprobación)
    @Override
    public PostulacionCoordinadorDetalleDto detalleCoordinador(Long postulacionId, String authName) {

        Usuario coordinador = usuarioRepository.findByEmail(authName);
        if (coordinador == null) {
            throw new RuntimeException("Coordinador no encontrado: " + authName);
        }
        if (coordinador.getArea() == null || coordinador.getArea().getId() == null) {
            throw new RuntimeException("El coordinador no tiene área asignada");
        }

        Postulacion p = postulacionRepository.findById(postulacionId)
                .orElseThrow(() -> new RuntimeException("Postulación no encontrada"));

        Vacante v = p.getVacanteIdvacante();
        if (v == null || v.getAreasdgpIdarea() == null || v.getAreasdgpIdarea().getId() == null) {
            throw new RuntimeException("La postulación no tiene vacante/área válida");
        }

        // ✅ Seguridad: solo puede ver si la vacante pertenece a su área
        if (!v.getAreasdgpIdarea().getId().equals(coordinador.getArea().getId())) {
            throw new RuntimeException("No autorizado para ver esta postulación");
        }

        Usuario prestador = p.getUsuariosIdusuario();
        StatusPostulacion est = p.getEstatusIdestatus();

        String prestadorNombre = (prestador == null) ? "—"
                : (prestador.getNombre() + " " + prestador.getPrimerapellido()
                + (prestador.getSegundoapellido() != null ? (" " + prestador.getSegundoapellido()) : ""));
        String prestadorEmail = (prestador != null) ? prestador.getEmail() : "—";
        String estTexto = mapEstatusTexto(est != null ? est.getId() : null);

        // ===== COMPROBACIÓN (carrera / modalidad / horario) =====

        // Carrera vacante
        Long vacCarreraId = null;
        String vacCarreraNom = null;
        try {
            if (v.getCarrerasIdcarrera() != null && v.getCarrerasIdcarrera().getId() != null) {
                // ✅ Integer -> Long
                vacCarreraId = Long.valueOf(v.getCarrerasIdcarrera().getId());
                vacCarreraNom = v.getCarrerasIdcarrera().getNombre();
            }
        } catch (Exception ignored) {}

        // Carrera prestador
        Long presCarreraId = null;
        String presCarreraNom = null;
        try {
            if (prestador != null && prestador.getCarrerasIdcarrera() != null && prestador.getCarrerasIdcarrera().getId() != null) {
                // ✅ Integer -> Long (por si su id también es Integer)
                presCarreraId = Long.valueOf(prestador.getCarrerasIdcarrera().getId());
                presCarreraNom = prestador.getCarrerasIdcarrera().getNombre();
            }
        } catch (Exception ignored) {}

        boolean cumpleCarrera = (vacCarreraId != null && presCarreraId != null && vacCarreraId.equals(presCarreraId));

        // Modalidad vacante
        Long vacModalidadId = null;
        String vacModalidadNom = null;
        try {
            if (v.getModalidadesIdmodalidad() != null && v.getModalidadesIdmodalidad().getId() != null) {
                // ✅ Integer -> Long
                vacModalidadId = Long.valueOf(v.getModalidadesIdmodalidad().getId());
                vacModalidadNom = v.getModalidadesIdmodalidad().getNombre();
            }
        } catch (Exception ignored) {}

        // Como Usuario normalmente NO tiene modalidad, por ahora lo dejamos true (validación manual)
        boolean cumpleModalidad = true;

        // Horario vacante
        Long vacHorarioId = null;
        String vacHorarioNom = null;
        try {
            if (v.getHorariosIdhorario() != null && v.getHorariosIdhorario().getId() != null) {
                // ✅ Integer -> Long
                vacHorarioId = Long.valueOf(v.getHorariosIdhorario().getId());
                vacHorarioNom = v.getHorariosIdhorario().getNombre();
            }
        } catch (Exception ignored) {}

        // Usuario normalmente no trae horario, lo dejamos true por ahora
        boolean cumpleHorario = true;

        String requisitos = null;
        try {
            requisitos = v.getRequisitos();
        } catch (Exception ignored) {}

        return new PostulacionCoordinadorDetalleDto(
                p.getId(),
                p.getFechaPostulacion(),
                (prestador != null ? prestador.getId() : null),
                prestadorNombre,
                prestadorEmail,
                (v != null ? v.getId() : null),
                (v != null ? v.getNombrePuesto() : null),
                (est != null ? est.getId() : null),
                estTexto,
                vacCarreraId, vacCarreraNom,
                presCarreraId, presCarreraNom,
                cumpleCarrera,
                vacModalidadId, vacModalidadNom, cumpleModalidad,
                vacHorarioId, vacHorarioNom, cumpleHorario,
                requisitos,
                p.getComentarios()
        );
    }

    // ✅ Coordinador -> cambiar estatus (aceptar/rechazar)
    @Override
    public void cambiarEstatus(Long postulacionId, Long estatusId, String authName) {

        Usuario coordinador = usuarioRepository.findByEmail(authName);
        if (coordinador == null) {
            throw new RuntimeException("Coordinador no encontrado: " + authName);
        }
        if (coordinador.getArea() == null || coordinador.getArea().getId() == null) {
            throw new RuntimeException("El coordinador no tiene área asignada");
        }

        Postulacion p = postulacionRepository.findById(postulacionId)
                .orElseThrow(() -> new RuntimeException("Postulación no encontrada"));

        Vacante v = p.getVacanteIdvacante();
        if (v == null || v.getAreasdgpIdarea() == null || v.getAreasdgpIdarea().getId() == null) {
            throw new RuntimeException("La postulación no tiene vacante/área válida");
        }

        // ✅ Seguridad: solo si pertenece a su área
        if (!v.getAreasdgpIdarea().getId().equals(coordinador.getArea().getId())) {
            throw new RuntimeException("No autorizado");
        }

        StatusPostulacion nuevo = statusPostulacionRepository.findById(estatusId)
                .orElseThrow(() -> new RuntimeException("Estatus no encontrado"));

        p.setEstatusIdestatus(nuevo);
        // ✅ Si fue aceptado (estatus 3) crear actividad inicial
        if (estatusId == 3L) {

            asignacionService.crearAsignacionDesdePostulacion(p);
            Actividad actividad = new Actividad();
            actividad.setDescripcion("Inicio de servicio social");
            actividad.setHorasDestinadas(BigDecimal.ZERO);
            actividad.setFechaActividad(LocalDate.now());
            actividad.setEstatusActividad("ACTIVA");
            actividad.setTipoActividad("SERVICIO");
            actividad.setIdUsuario(p.getUsuariosIdusuario());

            actividadRepository.save(actividad);
        }
        postulacionRepository.save(p);
    }

    // ----------------- MAPPERS -----------------

    public PostulacionDto convertirEntidadADTO(Postulacion postulacion) {
        PostulacionDto dto = new PostulacionDto();
        dto.setId(postulacion.getId());
        dto.setFechaPostulacion(postulacion.getFechaPostulacion());
        dto.setComentarios(postulacion.getComentarios());

        if (postulacion.getUsuariosIdusuario() != null) {
            dto.setUsuariosIdusuario(postulacion.getUsuariosIdusuario().getId());
        }

        if (postulacion.getEstatusIdestatus() != null) {
            dto.setEstatusIdestatus(postulacion.getEstatusIdestatus().getId());
        }

        if (postulacion.getVacanteIdvacante() != null) {
            dto.setVacanteidVacante(postulacion.getVacanteIdvacante().getId());
        }

        return dto;
    }

    public Postulacion convertirDTOAEntidad(PostulacionDto dto) {

        Usuario usuario = usuarioRepository.findById(dto.getUsuariosIdusuario())
                .orElseThrow(() -> new RuntimeException("usuario no encontrado"));

        StatusPostulacion status = statusPostulacionRepository.findById(dto.getEstatusIdestatus())
                .orElseThrow(() -> new RuntimeException("estatus no encontrado"));

        Vacante vacante = vacanteRepository.findById(dto.getVacanteidVacante())
                .orElseThrow(() -> new RuntimeException("vacante no encontrada"));

        Postulacion postulacion = new Postulacion();
        postulacion.setId(dto.getId());
        postulacion.setComentarios(dto.getComentarios());

        postulacion.setFechaPostulacion(
                dto.getFechaPostulacion() != null ? dto.getFechaPostulacion() : OffsetTime.now()
        );

        postulacion.setEstatusIdestatus(status);
        postulacion.setUsuariosIdusuario(usuario);
        postulacion.setVacanteIdvacante(vacante);

        return postulacion;
    }

    // ✅ helper para estatus
    private String mapEstatusTexto(Long estatusId) {
        if (estatusId == null) return "Nuevo";
        if (estatusId == 1L) return "Nuevo";
        if (estatusId == 2L) return "Rechazado";
        if (estatusId == 3L) return "Aceptado";
        return "Nuevo";
    }

    @Override
    public void gestionarCandidatos(List<Long> ids, Long statusId, String mensajeExtra) {

        // 1. Usar statusPostulacionRepository (nombre correcto)
        StatusPostulacion nuevoStatus = statusPostulacionRepository.findById(statusId)
                .orElseThrow(() -> new RuntimeException("Estatus no encontrado"));

        // 2. Iterar
        for (Long idPostulacion : ids) {
            Postulacion p = postulacionRepository.findById(idPostulacion).orElse(null);
            if (p == null) continue;

            p.setEstatusIdestatus(nuevoStatus);

            // 3. Lógica según ID
            if (statusId == 2) { // Entrevista
                p.setComentarios("Entrevista requerida. Link: " + mensajeExtra);
            }
            else if (statusId == 3) { // Aceptado
                p.setComentarios("Aceptado. " + (mensajeExtra != null ? mensajeExtra : ""));
                // ✅ Usar asignacionService (ahora sí inyectado)
                asignacionService.crearAsignacionDesdePostulacion(p);
                Actividad actividad = new Actividad();
                actividad.setDescripcion("Inicio de servicio social");
                actividad.setHorasDestinadas(BigDecimal.ZERO);
                actividad.setFechaActividad(LocalDate.now());
                actividad.setEstatusActividad("ACTIVA");
                actividad.setTipoActividad("SERVICIO");
                actividad.setIdUsuario(p.getUsuariosIdusuario());

                actividadRepository.save(actividad);
            }
            else if (statusId == 4) { // Rechazado
                p.setComentarios("Rechazado: " + mensajeExtra);
            }

            postulacionRepository.save(p);
        }
    }

}
