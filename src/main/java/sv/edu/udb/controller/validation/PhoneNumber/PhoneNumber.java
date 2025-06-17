package sv.edu.udb.controller.validation.PhoneNumber;
// Esta anotación nos permite validar números de teléfono con un patrón REGEX personalizable.
// Se puede usar en campos, atributos de clase o como parte de una anotación compuesta.
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

//Esta anotación define dónde puedes usar tu anotación personalizada(en clases, atributos o para crear otras anotaciones)
@Target({TYPE, FIELD, ANNOTATION_TYPE})

//Define cuándo debe mantenerse la anotación(en este caso la anotación estará disponible en tiempo de ejecución)
@Retention(RUNTIME)

//le dice a Java que usa la clase PhoneNumberValidator para validar los valores que tengan esta anotación.
@Constraint(validatedBy = PhoneNumberValidator.class)

//Hace que la anotación aparezca en la documentación generada por Javadoc(Swagger)
@Documented
public @interface PhoneNumber {

    /**
     * Mensaje de error que se muestra si el número no es válido.
     * @return mensaje de error
     */
    String message() default "Número de teléfono inválido";

    /**
     * Patrón REGEX que debe cumplir el número de teléfono.
     * Por defecto, usa el formato internacional E.164.
     * Ejemplo: 1234-5678
     * @return patrón REGEX
     */
    String pattern() default "^\\d{4}-\\d{4}$"; // Formato E.164

    /**
     * Grupos de validación a los que pertenece esta anotación.
     * @return grupos de validación
     */
    Class<?>[] groups() default {};

    /**
     * Información adicional (payload) asociada a la validación.
     * Útil para transporte de información adicional.
     * @return payload
     */
    Class<? extends Payload>[] payload() default {};
}
