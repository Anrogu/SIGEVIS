package com.proyecto.SsYPp.Dto;

import com.proyecto.SsYPp.Entity.Asignacion;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link com.proyecto.SsYPp.Entity.Actividad}
 */
@Getter
@Setter
@NoArgsConstructor
public class ActividadDto implements Serializable {
    Long id;
    @Size(max = 255)
    String descripcion;
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
    Long idUsuario;
}