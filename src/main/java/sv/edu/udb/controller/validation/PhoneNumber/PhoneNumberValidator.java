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
    private Matcher matcher;

    /**
     * Método que inicializa el validador con el patrón definido en la anotación.
     * @param constraintAnnotation La anotación usada
     */
    @Override
    public void initialize(final PhoneNumber constraintAnnotation) {
        regexPattern = constraintAnnotation.pattern(); // Obtiene el patrón del campo
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
        return isValidPhoneNumber(phoneNumber);
    }

    /**
     * Comprueba si el número de teléfono coincide con el patrón REGEX establecido.
     *
     * @param phoneNumber El número de teléfono a comprobar
     * @return true si el número coincide con el patrón, false en caso contrario
     */
    private boolean isValidPhoneNumber(final String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            return false; // Si está vacío o es nulo, no es válido
        }

        // Compilamos el patrón y comprobamos si el número coincide
        pattern = Pattern.compile(regexPattern);
        matcher = pattern.matcher(phoneNumber);

        return matcher.matches(); // Devolvemos el resultado de la comparación
    }
}
