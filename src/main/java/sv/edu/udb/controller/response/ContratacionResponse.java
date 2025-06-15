package sv.edu.udb.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder(toBuilder = true)
@FieldNameConstants
public class ContratacionResponse {

    private Long id;

    private String empleadoNombre;

    private String cargoNombre;

    private String departamentoNombre;

    private String tipoContratacionNombre;

    private LocalDate fechaContratacion;

    private BigDecimal salario;

    private Boolean estado;
}
