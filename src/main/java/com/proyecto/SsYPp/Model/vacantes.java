import com.proyecto.SsYPp.Model.Convenio;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data; // Se asume que usas Lombok para getters/setters

import java.time.LocalDateTime;

@Entity
@Table(name = "Vacantes") // Nombre exacto de tu tabla en PostgreSQL
@Data // Anotación de Lombok para generar Getters, Setters, etc.
public class vacantes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PostgreSQL usa IDENTITY para auto-incremento
    private Long idVacantes; // bigint en PostgreSQL

    private String nombrePuesto; // character varying(255)

    private String descripcion; // text

    private Integer numeroPlazas; // integer

    private Boolean estatus; // boolean

    private LocalDateTime fechaPublicacion; // timestamp without time zone

    private String requisitos; // character varying(255) (Nullable)

    private String carrera; // character varying(255) (Nullable)

    // Claves Foráneas (Se representan como IDs simples en la Entidad,
    // a menos que uses relaciones @ManyToOne, que sería lo ideal).
    private Integer Perfiles_idPerfil; // integer

    private Long AreasDgp_idArea; // bigint

    private Integer Horarios_idHorario; // integer

    private Integer Modalidades_idModalidad; // integer

    private Long Asignaciones_idAsignacion; // bigint

    // Si no usas Lombok, debes agregar manualmente los Getters y Setters aquí.

    // Ejemplo de Constructor vacío (requerido por JPA)
    public vacantes() {
    }
}