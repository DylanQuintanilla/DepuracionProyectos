package sv.edu.udb.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class TipoContratacionRequest {
    @NotBlank(message = "El nombre del tipo de contratacion no puede ser vacio")
    @Size(min = 5,max = 50, message = "El nombre debe contener entre 5 a 50 caracteres")
    private String nombre;
}
