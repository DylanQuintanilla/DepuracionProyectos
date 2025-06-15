package sv.edu.udb.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContratacionRequest {

    @NotNull(message = "El empleado es obligatorio")
    private Long empleadoId;

    @NotNull(message = "El cargo es obligatorio")
    private Long cargoId;

    @NotNull(message = "El departamento es obligatorio")
    private Long departamentoId;

    @NotNull(message = "El tipo de contratacion es obligatorio")
    private Long tipoContratacionId;

    @DecimalMin(value = "0.1", message = "El precio debe ser al menos 0.1")
    @Digits(integer = 8, fraction = 2, message = "El precio debe tener como máximo 8 dígitos enteros y 2 decimales")
    @NotNull(message = "El salario es obligatorio")
    private BigDecimal salario;

    private Boolean estado;

}
