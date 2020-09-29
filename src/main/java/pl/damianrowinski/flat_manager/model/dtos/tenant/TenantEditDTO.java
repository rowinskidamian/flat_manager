package pl.damianrowinski.flat_manager.model.dtos.tenant;

import lombok.Data;
import pl.damianrowinski.flat_manager.validation.constraints.CheckDateNull;
import pl.damianrowinski.flat_manager.validation.constraints.CheckLeaseDates;
import pl.damianrowinski.flat_manager.validation.groups.AddressValidationGroup;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@CheckLeaseDates
public class TenantEditDTO {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @CheckDateNull
    private LocalDate leaseDateStart;

    @CheckDateNull
    private LocalDate leaseDateEnd;

    private Long roomId;

    private Double rentDiscount;

    @NotNull
    @Min(1)
    @Max(31)
    private Integer paymentDeadline;

    @Email
    @NotBlank
    private String email;

    @NotBlank(groups = AddressValidationGroup.class)
    private String cityName;
    @NotBlank(groups = AddressValidationGroup.class)
    private String streetName;

    @NotNull(groups = AddressValidationGroup.class)
    @Min(value = 1, groups = AddressValidationGroup.class)
    private Integer streetNumber;

    @Min(value = 1, groups = AddressValidationGroup.class)
    private Integer apartmentNumber;

}