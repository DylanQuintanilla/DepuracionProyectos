package sv.edu.udb.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthRequest {

    @NotBlank(message = "Por favor llenar el campo del usuario")
    @Size(min = 5, max = 20, message = "El usuario debe contener entre 5 a 20 caracteres")
    private String username;

    @NotBlank(message = "Por favor llenar el campo de la contraseña")
    @Size(max = 20, message = "La contraseña puede tener un maximo de 20 caracteres")
    private String password;
}
