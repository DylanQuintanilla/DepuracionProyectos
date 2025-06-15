package sv.edu.udb.controller.validation.Dui;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuiValidator implements ConstraintValidator<Dui, String> {

    private String regexPattern;
    private Pattern pattern;
    private Matcher matcher;

    @Override
    public void initialize(final Dui constraintAnnotation) {
        regexPattern = constraintAnnotation.pattern();
    }

    private boolean isValidDui(final String dui) {
        if (dui == null || dui.isBlank()) {
            return false;
        }

        pattern = Pattern.compile(regexPattern);
        matcher = pattern.matcher(dui);

        return matcher.matches();
    }

    @Override
    public boolean isValid(String dui, ConstraintValidatorContext context) {
        return isValidDui(dui);
    }
}
