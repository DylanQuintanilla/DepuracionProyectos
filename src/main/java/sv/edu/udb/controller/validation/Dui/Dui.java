package sv.edu.udb.controller.validation.Dui;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import sv.edu.udb.controller.validation.PhoneNumber.PhoneNumberValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = DuiValidator.class)
@Documented

public @interface Dui {

    String message() default "Número de Dui inválido";

    String pattern() default "^\\d{8}-\\d{1}$";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
