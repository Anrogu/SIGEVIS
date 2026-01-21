package com.proyecto.SsYPp.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

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
    @NotNull
    Long estatusIdestatus;
    Long vacanteidVacante;
}