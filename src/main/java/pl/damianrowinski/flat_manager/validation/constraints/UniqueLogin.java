package pl.damianrowinski.flat_manager.validation.constraints;

import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueLogin {
    String message() default "{uniqueLogin.error.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
