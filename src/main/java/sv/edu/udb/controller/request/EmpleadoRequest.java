package sv.edu.udb.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import jakarta.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true) //Ignora propiedades desconocidas
@JsonInclude(JsonInclude.Include.NON_NULL) //No incluye los valoros devueltos nulos

public class EmpleadoRequest {

    @NotBlank(message = "El DUI es obligatorio")
    @Pattern(regexp = "^\\d{8}-\\d{1}$", message = "El DUI debe tener el formato 12345678-9")
    private String dui;

    @NotBlank(message = "El nombre de la persona es obligatorio")
    @Size(min = 5, max = 50, message = "El nombre no puede exceder los 50 caracteres")
    private String nombre;

    @NotBlank(message = "El número de teléfono es obligatorio")
    @Pattern(regexp = "^\\d{4}-\\d{4}$", message = "El teléfono debe tener el formato 1234-5678")
    private String numeroTelefono;

    @NotBlank(message = "El correo institucional es obligatorio")
    @Email(message = "Debe ser un formato de correo electrónico válido")
    @Size(max = 50, message = "El correo no puede exceder los 50 caracteres")
    private String correo;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private Date fechaNacimiento;
}