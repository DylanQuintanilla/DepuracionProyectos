package sv.edu.udb.controller.validation.PhoneNumber;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Validador encargado de comprobar que un número de teléfono cumpla con un patrón REGEX específico.
 * Este validador se asocia con la anotación @PhoneNumber.
 */
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    private String regexPattern;
    private Pattern pattern;

    /**
     * Método que inicializa el validador con el patrón definido en la anotación.
     * @param constraintAnnotation La anotación usada
     */
    @Override
    public void initialize(final PhoneNumber constraintAnnotation) {
        regexPattern = constraintAnnotation.pattern(); // Obtiene el patrón del campo
        pattern = Pattern.compile(regexPattern); // Compilar el patrón aquí para optimizar rendimiento
    }

    /**
     * Valida si el número de teléfono dado cumple con el patrón definido.
     *
     * @param phoneNumber Número de teléfono a validar
     * @param context Contexto de validación
     * @return true si el número es válido, false en caso contrario
     */
    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            return false;
        }
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}
