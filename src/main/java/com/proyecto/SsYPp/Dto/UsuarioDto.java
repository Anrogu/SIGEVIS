package com.proyecto.SsYPp.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.proyecto.SsYPp.Entity.Usuario}
 */
@Getter
@Setter
@NoArgsConstructor
public class UsuarioDto implements Serializable {
    @NotNull
    @Size(max = 255)
    String nombre;
    @NotNull
    @Size(max = 255)
    String segundoapellido;
    @NotNull
    @Size(max = 255)
    String email;
    @NotNull
    @Size(max = 255)
    String primerapellido;
    @NotNull
    @Size(max = 255)
    String password;
    @NotNull
    Boolean status;
    Integer idrol;
    Integer carrerasIdcarrera;
}