package pl.damianrowinski.flat_manager.validation.validators;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.damianrowinski.flat_manager.services.ValidationService;
import pl.damianrowinski.flat_manager.validation.constraints.UniqueEmail;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
@Slf4j
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final ValidationService validationService;

    @Override
    public boolean isValid(String source, ConstraintValidatorContext context) {
        log.info("Attempt to valid unique email: " + source);
        return validationService.checkUniqueEmail(source);
    }
}
