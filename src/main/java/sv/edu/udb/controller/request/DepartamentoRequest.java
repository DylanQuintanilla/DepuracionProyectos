package sv.edu.udb.controller.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class DepartamentoRequest {

    @NotBlank(message = "El nombre del departamento no puede estar vacio")
    @Size(min = 5, max = 50, message = "El nombre del departamento debe contener entre 5 a 50 caracteres")
    private String nombre;

    @NotBlank(message = "La descripcion del departamento no puede estar vacio")
    @Size(min = 5, max = 50, message = "La descipcion del departamento debe contener entre 5 a 50 carateres")
    private String descripcion;

}
