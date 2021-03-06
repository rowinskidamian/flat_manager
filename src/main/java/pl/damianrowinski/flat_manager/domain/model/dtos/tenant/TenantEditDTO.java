package pl.damianrowinski.flat_manager.domain.model.dtos.tenant;

import lombok.Data;
import pl.damianrowinski.flat_manager.validation.constraints.CheckDatePattern;
import pl.damianrowinski.flat_manager.validation.constraints.CheckLeaseDates;
import pl.damianrowinski.flat_manager.validation.constraints.UniqueEmail;
import pl.damianrowinski.flat_manager.validation.groups.AddTenantGroup;
import pl.damianrowinski.flat_manager.validation.groups.AddressValidationGroup;
import pl.damianrowinski.flat_manager.validation.groups.EditTenantGroup;

import javax.persistence.Transient;
import javax.validation.constraints.*;

@Data
@CheckLeaseDates
public class TenantEditDTO {

    private Long id;

    private String loggedUserName;

    @NotBlank
    @CheckDatePattern(groups = EditTenantGroup.class)
    private String leaseDateStart;

    @NotBlank
    @CheckDatePattern(groups = EditTenantGroup.class)
    private String leaseDateEnd;

    private Double rentDiscount;

    @NotNull
    @Min(1)
    @Max(31)
    private Integer paymentDeadline;

    private Long roomId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Transient
    private String fullName;

    @Email
    @NotBlank
    @UniqueEmail(groups = AddTenantGroup.class)
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

    public String getFullName() {
        return fullName = firstName + " " + lastName;
    }

}
