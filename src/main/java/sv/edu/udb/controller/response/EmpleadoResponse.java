package sv.edu.udb.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.util.Date;

@Getter
@Setter
@Builder(toBuilder = true) //Es para poder modificar el objeto luego de creado
@FieldNameConstants //Para generar constantes con los nombres de las propiedades
public class EmpleadoResponse {

    private Long id;

    private String dui;

    private String nombre;

    private String numeroTelefono;

    private String correo;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date fechaNacimiento;

    //Agregar pero despues ahorita solo recordatorio que automaticamente aparezca la lista de las contrataciones que tenga este empleado paginado
}
