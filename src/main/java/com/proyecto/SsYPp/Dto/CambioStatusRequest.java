package com.proyecto.SsYPp.Dto;

import lombok.Data;
import java.util.List;

@Data
public class CambioStatusRequest {
    private List<Long> postulacionIds;
    private Long nuevoStatusId;
    private String mensaje;
}