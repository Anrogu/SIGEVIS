package com.proyecto.SsYPp.Dto;

import com.proyecto.SsYPp.Entity.Postulacion;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.time.OffsetTime;

/**
 * DTO for {@link com.proyecto.SsYPp.Entity.Asignacion}
 */

@Getter
@Setter
@NoArgsConstructor
public class AsignacionDto implements Serializable {
    Long id;
    @NotNull
    OffsetTime fechaInicio;
    @NotNull
    OffsetTime fechaFin;
    @NotNull
    Boolean estatus;
    @NotNull
    Integer usuariosIdusuario;
    @NotNull
    Integer postulacionesIdpostulacion;
}