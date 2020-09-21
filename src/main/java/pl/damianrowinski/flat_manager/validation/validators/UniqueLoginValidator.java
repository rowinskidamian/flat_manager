package pl.damianrowinski.flat_manager.validation.validators;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.damianrowinski.flat_manager.model.repositories.UserRepository;
import pl.damianrowinski.flat_manager.validation.constraints.UniqueLogin;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
@Slf4j

public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, String> {

    private final UserRepository userRepository;

    @Override
    public void initialize(UniqueLogin constraintAnnotation) {

    }

    @Override
    public boolean isValid(String valueToValid, ConstraintValidatorContext context) {
        log.info("Attempt to valid unique login: " + valueToValid);
        return !userRepository.existsByLogin(valueToValid);
    }
}
