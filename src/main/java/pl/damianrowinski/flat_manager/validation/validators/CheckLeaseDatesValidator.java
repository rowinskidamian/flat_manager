package pl.damianrowinski.flat_manager.validation.validators;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.damianrowinski.flat_manager.domain.model.dtos.tenant.TenantEditDTO;
import pl.damianrowinski.flat_manager.validation.constraints.CheckLeaseDates;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class CheckLeaseDatesValidator implements ConstraintValidator<CheckLeaseDates, TenantEditDTO> {
    @Override
    public boolean isValid(TenantEditDTO tenantEditDTO, ConstraintValidatorContext context) {
        String leaseDateStartString = tenantEditDTO.getLeaseDateStart();
        String leaseDateEndString = tenantEditDTO.getLeaseDateEnd();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate leaseDateStart = LocalDate.parse(leaseDateStartString, formatter);
        LocalDate leaseDateEnd = LocalDate.parse(leaseDateEndString, formatter);

        boolean isStartBeforeEnd = leaseDateStart.isBefore(leaseDateEnd);

        if (!isStartBeforeEnd) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{checkLeaseDates.validation.message}")
                    .addPropertyNode("leaseDateEnd").addConstraintViolation();
        }
        return isStartBeforeEnd;
    }
}
