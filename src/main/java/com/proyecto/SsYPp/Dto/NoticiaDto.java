package com.proyecto.SsYPp.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.OffsetTime;

/**
 * DTO for {@link com.proyecto.SsYPp.Entity.Noticia}
 */
@Getter
@Setter
@NoArgsConstructor
public class NoticiaDto implements Serializable {
    Long id;
    @NotNull
    @Size(max = 255)
    String titulo;
    @NotNull
    String contenido;
    @NotNull
    @Size(max = 50)
    String estatus;
    @NotNull
    OffsetTime fechaPublicacion;
    @NotNull
    Integer autoridIdusuario;
    String nombreAutor;
}