package pl.damianrowinski.flat_manager.module1_crud.validation.validators;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.damianrowinski.flat_manager.module1_crud.validation.constraints.CheckDateNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j

public class CheckDateNullValidator implements ConstraintValidator<CheckDateNull, LocalDate> {

    @Override
    public boolean isValid(LocalDate valueToValid, ConstraintValidatorContext context) {
        log.debug("Attempt to valid date is null: " + valueToValid);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return !(LocalDate.parse("1111-11-11", formatter)).equals(valueToValid);
    }
}
