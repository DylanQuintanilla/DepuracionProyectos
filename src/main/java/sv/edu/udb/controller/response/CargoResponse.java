package sv.edu.udb.controller.response;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@Builder(toBuilder = true)
@FieldNameConstants
public class CargoResponse {

    private Long id;

    private String cargo;

    private String descripcion;

    private Boolean jefatura;
}
