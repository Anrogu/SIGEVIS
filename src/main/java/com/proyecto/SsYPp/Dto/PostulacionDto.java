package com.proyecto.SsYPp.Dto;

import com.proyecto.SsYPp.Entity.StatusPostulacion;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.OffsetTime;

/**
 * DTO for {@link com.proyecto.SsYPp.Entity.Postulacion}
 */
@Getter
@Setter
@NoArgsConstructor
public class PostulacionDto implements Serializable {
    Long id;
    @NotNull
    OffsetTime fechaPostulacion;
    String comentarios;
    @NotNull
    Long usuariosIdusuario;
    Long estatusIdestatus;
    Long asignaciones;
}