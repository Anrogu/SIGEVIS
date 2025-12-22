package com.proyecto.SsYPp.Service.Impl;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.proyecto.SsYPp.Dto.UsuarioDto;
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
        vacanteDto.setIdVacantes(vacante.getIdVacantes());
        vacanteDto.setCarrera(vacante.getCarrera());
        vacanteDto.setEstatus(vacante.getEstatus());
        vacanteDto.setDescripcion(vacante.getDescripcion());
        vacanteDto.setNumeroPlazas(vacante.getNumeroPlazas());
        vacanteDto.setFechaPublicacion(vacante.getFechaPublicacion());
        vacanteDto.setRequisitos(vacante.getRequisitos());
        vacanteDto.setNombrePuesto(vacante.getNombrePuesto());
        vacanteDto.setAsignaciones_idAsignacion(vacante.getAsignaciones_idAsignacion());
        vacanteDto.setAreasDgp_idArea(vacante.getAreasDgp_idArea());
        vacanteDto.setModalidades_idModalidad(vacante.getModalidades_idModalidad());
        vacanteDto.setPerfiles_idPerfil(vacante.getPerfiles_idPerfil());
        vacanteDto.setHorarios_idHorario(vacante.getHorarios_idHorario());

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

        Vacante vacante = new Vacante();
        vacante.setIdVacantes(dto.getIdVacantes());
        vacante.setEstatus(dto.getEstatus());
        vacante.setNombrePuesto(dto.getNombrePuesto());
        vacante.setDescripcion(dto.getDescripcion());
        vacante.setNumeroPlazas(dto.getNumeroPlazas());
        vacante.setFechaPublicacion(dto.getFechaPublicacion());
        vacante.setCarrera(dto.getCarrera());
        vacante.setRequisitos(dto.getRequisitos());
        vacante.setAsignaciones_idAsignacion(asignacion.getId());
        vacante.setAreasDgp_idArea(areaDgp.getId());
        vacante.setModalidades_idModalidad(modalidad.getId());
        vacante.setPerfiles_idPerfil(perfil.getId());
        vacante.setHorarios_idHorario(horario.getId());
        return vacante;
    }
}
