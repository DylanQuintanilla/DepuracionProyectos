package sv.edu.udb.controller.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CargoRequest {

    @NotBlank(message = "El valor de cargo no puede ser nulo")
    @Size(min = 5, max = 50, message = "El valor de cargo debe contener entre 5 a 50 caracteres")
    private String cargo;

    @NotBlank(message = "La descripcion no pueder ser nula")
    @Size(min = 5, max = 100, message = "El valor de cargo debe contener entre 5 a 100 caracteres")
    private String descripcion;

    @NotNull(message = "El valor de jefatura no puede ser nula")
    private Boolean jefatura;
}
