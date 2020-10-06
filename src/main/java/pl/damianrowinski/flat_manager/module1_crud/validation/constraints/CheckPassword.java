package pl.damianrowinski.flat_manager.module1_crud.validation.constraints;


import pl.damianrowinski.flat_manager.module1_crud.validation.validators.CheckPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CheckPasswordValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPassword {
    String message() default "{checkPassword.validation.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
