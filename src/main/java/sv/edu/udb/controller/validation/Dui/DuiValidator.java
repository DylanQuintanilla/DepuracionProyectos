package sv.edu.udb.controller.validation.Dui;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuiValidator implements ConstraintValidator<Dui, String> {

    private String regexPattern;
    private Pattern pattern;

    @Override
    public void initialize(final Dui constraintAnnotation) {
        regexPattern = constraintAnnotation.pattern();
        pattern = Pattern.compile(regexPattern);
    }


    @Override
    public boolean isValid(String dui, ConstraintValidatorContext context) {
        if (dui == null || dui.isBlank()) {
            return false;
        }
        Matcher matcher = pattern.matcher(dui);
        return matcher.matches();
    }
}
