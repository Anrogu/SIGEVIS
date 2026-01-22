package com.proyecto.SsYPp.Service.Impl;

import com.proyecto.SsYPp.Dto.VacanteDto;
import com.proyecto.SsYPp.Entity.*;
import com.proyecto.SsYPp.Repository.*;
import com.proyecto.SsYPp.Service.VacanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VacanteServiceImpl implements VacanteService {

    @Autowired private VacanteRepository vacanteRepository;
    @Autowired private AreaDgpRepository areaDgpRepository;
    @Autowired private ModalidadRepository modalidadRepository;
    @Autowired private HorarioRepository horarioRepository;
    @Autowired private CarreraRepository carreraRepository;
    @Autowired private UsuarioRepository usuarioRepository;

    @Override
    public VacanteDto create(VacanteDto dto) {
        Vacante v = convertirDTOAEntidad(dto);
        return convertirEntidadADTO(vacanteRepository.save(v));
    }

    @Override
    public List<VacanteDto> getAll() {
        return vacanteRepository.findAll()
                .stream()
                .map(this::convertirEntidadADTO)
                .collect(Collectors.toList());
    }

    @Override
    public VacanteDto get(Long id) {
        return convertirEntidadADTO(
                vacanteRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Vacante no encontrada"))
        );
    }

    @Override
    public void delete(Long id) {
        vacanteRepository.deleteById(id);
    }

    @Override
    public VacanteDto update(VacanteDto dto) {

        Vacante existente = vacanteRepository.findById(dto.getIdVacantes())
                .orElseThrow(() -> new RuntimeException("Vacante no encontrada"));

        Modalidad modalidad = modalidadRepository.findById(dto.getModalidadesIdModalidad())
                .orElseThrow(() -> new RuntimeException("Modalidad no encontrada"));

        AreaDgp area = areaDgpRepository.findById(dto.getAreasDgpIdArea())
                .orElseThrow(() -> new RuntimeException("Área no encontrada"));

        Horario horario = horarioRepository.findById(dto.getHorariosIdHorario())
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));

        Carrera carrera = carreraRepository.findById(dto.getCarrerasIdCarrera().intValue())
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));

        existente.setNombrePuesto(dto.getNombrePuesto());
        existente.setDescripcion(dto.getDescripcion());
        existente.setNumeroPlazas(dto.getNumeroPlazas());
        existente.setEstatus(dto.getEstatus());
        existente.setFechaPublicacion(dto.getFechaPublicacion());
        existente.setRequisitos(dto.getRequisitos());

        existente.setAreasdgpIdarea(area);
        existente.setModalidadesIdmodalidad(modalidad);
        existente.setHorariosIdhorario(horario);
        existente.setCarrerasIdcarerra(carrera);

        Vacante guardada = vacanteRepository.save(existente);
        return convertirEntidadADTO(guardada);
    }

    private VacanteDto convertirEntidadADTO(Vacante v) {
        VacanteDto dto = new VacanteDto();

        dto.setIdVacantes(v.getId());
        dto.setNombrePuesto(v.getNombrePuesto());
        dto.setDescripcion(v.getDescripcion());
        dto.setNumeroPlazas(v.getNumeroPlazas());
        dto.setEstatus(v.getEstatus());
        dto.setFechaPublicacion(v.getFechaPublicacion());
        dto.setRequisitos(v.getRequisitos());

        // ✅ Área (normalmente siempre existe, pero por seguridad)
        if (v.getAreasdgpIdarea() != null) {
            dto.setAreasDgpIdArea(v.getAreasdgpIdarea().getId());
            dto.setAreaNombre(v.getAreasdgpIdarea().getNombre());
        } else {
            dto.setAreasDgpIdArea(null);
            dto.setAreaNombre(null);
        }

        // ✅ Modalidad (puede ser null en registros viejos / insert manual)
        if (v.getModalidadesIdmodalidad() != null) {
            dto.setModalidadesIdModalidad(Long.valueOf(v.getModalidadesIdmodalidad().getId()));
        } else {
            dto.setModalidadesIdModalidad(null);
        }

        // ✅ Horario (puede ser null)
        if (v.getHorariosIdhorario() != null) {
            dto.setHorariosIdHorario(Long.valueOf(v.getHorariosIdhorario().getId()));
        } else {
            dto.setHorariosIdHorario(null);
        }

        // ✅ Carrera (puede ser null)
        if (v.getCarrerasIdcarerra() != null) {
            dto.setCarrerasIdCarrera((long) v.getCarrerasIdcarerra().getId());
        } else {
            dto.setCarrerasIdCarrera(null);
        }

        // ✅ CreadoPor (puede ser null)
        if (v.getCreadoPor() != null) {
            Usuario u = v.getCreadoPor();
            dto.setUsuariosIdUsuario(u.getId());

            String nombre = (u.getNombre() != null) ? u.getNombre() : "";
            String ap1 = (u.getPrimerapellido() != null) ? u.getPrimerapellido() : "";
            String ap2 = (u.getSegundoapellido() != null) ? u.getSegundoapellido() : "";

            dto.setCreadoPorNombre((nombre + " " + ap1 + " " + ap2).trim());
        }

        return dto;
    }

    private Vacante convertirDTOAEntidad(VacanteDto dto) {

        Modalidad modalidad = modalidadRepository.findById(dto.getModalidadesIdModalidad())
                .orElseThrow(() -> new RuntimeException("Modalidad no encontrada"));

        AreaDgp area = areaDgpRepository.findById(dto.getAreasDgpIdArea())
                .orElseThrow(() -> new RuntimeException("Área no encontrada"));

        Horario horario = horarioRepository.findById(dto.getHorariosIdHorario())
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));

        Carrera carrera = carreraRepository.findById(dto.getCarrerasIdCarrera().intValue())
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuariosIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Vacante v = new Vacante();
        v.setId(dto.getIdVacantes());
        v.setNombrePuesto(dto.getNombrePuesto());
        v.setDescripcion(dto.getDescripcion());
        v.setNumeroPlazas(dto.getNumeroPlazas());
        v.setEstatus(dto.getEstatus());
        v.setFechaPublicacion(dto.getFechaPublicacion());
        v.setRequisitos(dto.getRequisitos());

        v.setAreasdgpIdarea(area);
        v.setModalidadesIdmodalidad(modalidad);
        v.setHorariosIdhorario(horario);
        v.setCarrerasIdcarerra(carrera);
        v.setCreadoPor(usuario);

        return v;
    }
}
