package pl.damianrowinski.flat_manager.validation.validators;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.damianrowinski.flat_manager.model.dtos.tenant.TenantEditDTO;
import pl.damianrowinski.flat_manager.validation.constraints.CheckLeaseDates;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

@Component
@Slf4j
public class CheckLeaseDatesValidator implements ConstraintValidator<CheckLeaseDates, TenantEditDTO> {
    @Override
    public boolean isValid(TenantEditDTO tenantEditDTO, ConstraintValidatorContext context) {
        LocalDate leaseDateStart = tenantEditDTO.getLeaseDateStart();
        LocalDate leaseDateEnd = tenantEditDTO.getLeaseDateEnd();
        boolean isStartBeforeEnd = leaseDateStart.isBefore(leaseDateEnd);

        if (!isStartBeforeEnd) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{checkLeaseDates.validation.message}")
                    .addPropertyNode("leaseDateEnd").addConstraintViolation();
        }
        return isStartBeforeEnd;
    }
}
