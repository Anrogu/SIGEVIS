package com.proyecto.SsYPp.Dto;

import com.proyecto.SsYPp.Entity.Asignacion;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link com.proyecto.SsYPp.Entity.Actividad}
 */
@Value
public class ActividadDto implements Serializable {
    Long id;
    @NotNull
    Long descripcion;
    @NotNull
    BigDecimal horasDestinadas;
    @NotNull
    LocalDate fechaActividad;
    @NotNull
    @Size(max = 255)
    String estatusActividad;
    @Size(max = 255)
    String tipoActividad;
    @NotNull
    UsuarioDto idUsuario;
    @NotNull
    Asignacion idAsignacion;
}