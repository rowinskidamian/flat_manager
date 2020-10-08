package pl.damianrowinski.flat_manager.validation.validators;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.damianrowinski.flat_manager.validation.constraints.CheckDatePattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

@Component
@Slf4j
public class CheckDatePatternValidator implements ConstraintValidator<CheckDatePattern, String> {
    @Override
    public boolean isValid(String source, ConstraintValidatorContext context) {
        String dateValidPattern = "\\d{4}-\\d\\d-\\d\\d";
        return Pattern.matches(dateValidPattern, source);
    }
}
