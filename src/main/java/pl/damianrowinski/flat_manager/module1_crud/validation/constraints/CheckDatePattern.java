package pl.damianrowinski.flat_manager.module1_crud.validation.constraints;

import pl.damianrowinski.flat_manager.module1_crud.validation.validators.CheckDatePatternValidator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CheckDatePatternValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckDatePattern {
    String message() default "{checkDatePattern.validation.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
