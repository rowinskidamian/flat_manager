package pl.damianrowinski.flat_manager.validation.validators;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.damianrowinski.flat_manager.domain.model.dtos.user.UserAddDTO;
import pl.damianrowinski.flat_manager.validation.constraints.CheckPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@Slf4j

public class CheckPasswordValidator implements ConstraintValidator<CheckPassword, UserAddDTO> {

    @Override
    public boolean isValid(UserAddDTO userData, ConstraintValidatorContext constraintContext) {
        String password = userData.getPassword();
        String passwordCheck = userData.getPasswordCheck();
        log.info("Attempt to valid if password is equal.");
        boolean isPasswordsEqual = password.equals(passwordCheck);

        if (!isPasswordsEqual){
            constraintContext.disableDefaultConstraintViolation();
            constraintContext.buildConstraintViolationWithTemplate("{checkPassword.validation.message}")
                    .addPropertyNode("passwordCheck").addConstraintViolation();
        }
        return isPasswordsEqual;
    }
}
