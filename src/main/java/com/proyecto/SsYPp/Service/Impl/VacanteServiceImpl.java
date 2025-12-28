package com.proyecto.SsYPp.Service.Impl;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.proyecto.SsYPp.Dto.UsuarioDto;
import com.proyecto.SsYPp.Dto.VacanteDto;
import com.proyecto.SsYPp.Entity.*;
import com.proyecto.SsYPp.Repository.*;
import com.proyecto.SsYPp.Service.VacanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VacanteServiceImpl implements VacanteService {
    @Autowired
    VacanteRepository vacanteRepository;
    @Autowired
    AreaDgpRepository areaDgpRepository;
    @Autowired
    AsignacionRepository asignacionRepository;
    @Autowired
    PerfilRepository perfilRepository;
    @Autowired
    ModalidadRepository modalidadRepository;
    @Autowired
    HorarioRepository horarioRepository;
    @Autowired
    CarreraRepository carreraRepository;

    @Override
    public VacanteDto create(VacanteDto vacanteDto) {
        Vacante vacante = convertirDTOAEntidad(vacanteDto);
        Vacante guardada = vacanteRepository.save(vacante);
        return convertirEntidadADTO(guardada);
    }

    @Override
    public List<VacanteDto> getAll() {
        List<Vacante> vacantes = vacanteRepository.findAll();
        return vacantes.stream()
                .map(this::convertirEntidadADTO)
                .collect(Collectors.toList());
    }

    @Override
    public VacanteDto get(Long id) {
        Vacante vacante = vacanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("vacante no encontrada"));
        return convertirEntidadADTO(vacante);
    }

    @Override
    public void delete(Long id) {
        vacanteRepository.deleteById(id);
    }

    @Override
    public VacanteDto update(VacanteDto vacanteDto) {
        Vacante vacante = convertirDTOAEntidad(vacanteDto);
        Vacante actualizada = vacanteRepository.save(vacante);
        return convertirEntidadADTO(actualizada);
    }
    private VacanteDto convertirEntidadADTO(Vacante vacante) {
        VacanteDto vacanteDto = new VacanteDto();
        vacanteDto.setIdVacantes(vacante.getId());
        vacanteDto.setCarreras_idCarrera(vacante.getCarrerasIdcarerra().getId().intValue());
        vacanteDto.setEstatus(vacante.getEstatus());
        vacanteDto.setDescripcion(vacante.getDescripcion());
        vacanteDto.setNumeroPlazas(vacante.getNumeroPlazas());
        vacanteDto.setFechaPublicacion(OffsetTime.from(vacante.getFechaPublicacion()));
        vacanteDto.setRequisitos(vacante.getRequisitos());
        vacanteDto.setNombrePuesto(vacante.getNombrePuesto());
        vacanteDto.setAsignaciones_idAsignacion(vacante.getAsignacionesIdasignacion().getId());
        vacanteDto.setAreasDgp_idArea(vacante.getAreasdgpIdarea().getId());
        vacanteDto.setModalidades_idModalidad(vacante.getModalidadesIdmodalidad().getId());
        vacanteDto.setPerfiles_idPerfil(vacante.getPerfilesIdperfil().getId());
        vacanteDto.setHorarios_idHorario(vacante.getHorariosIdhorario().getId());

        return vacanteDto;
    }

    // Metodo Dto a Entidad
    private Vacante convertirDTOAEntidad(VacanteDto dto) {
        Asignacion asignacion = asignacionRepository.findById(dto.getAsignaciones_idAsignacion())
                .orElseThrow(() -> new RuntimeException("Asignacion con ID " + dto.getAsignaciones_idAsignacion() + " no existe."));
        Perfil perfil = perfilRepository.findById(Long.valueOf(dto.getPerfiles_idPerfil()))
                .orElseThrow(() -> new RuntimeException("perfil no encontrado"));
        Modalidad modalidad = modalidadRepository.findById(Long.valueOf(dto.getModalidades_idModalidad()))
                .orElseThrow(() -> new RuntimeException("modalidad no encontrada"));
        AreaDgp areaDgp = areaDgpRepository.findById(Long.valueOf(dto.getAreasDgp_idArea()))
                .orElseThrow(() -> new RuntimeException("areaDgp no encontrada"));
        Horario horario= horarioRepository.findById(Long.valueOf(dto.getHorarios_idHorario()))
                .orElseThrow(() -> new RuntimeException("perfil no encontrado"));
        Carrera carrera=carreraRepository.findById(dto.getCarreras_idCarrera())
                .orElseThrow(()-> new RuntimeException("carrera no encontrada"));

        Vacante vacante = new Vacante();
        vacante.setId(dto.getIdVacantes());
        vacante.setEstatus(dto.getEstatus());
        vacante.setNombrePuesto(dto.getNombrePuesto());
        vacante.setDescripcion(dto.getDescripcion());
        vacante.setNumeroPlazas(dto.getNumeroPlazas());
        vacante.setFechaPublicacion(dto.getFechaPublicacion().toLocalTime());
        vacante.setCarrerasIdcarerra(carrera);
        vacante.setRequisitos(dto.getRequisitos());
        vacante.setAsignacionesIdasignacion(asignacion);
        vacante.setAreasdgpIdarea(areaDgp);
        vacante.setModalidadesIdmodalidad(modalidad);
        vacante.setPerfilesIdperfil(perfil);
        vacante.setHorariosIdhorario(horario);
        return vacante;
    }
}
