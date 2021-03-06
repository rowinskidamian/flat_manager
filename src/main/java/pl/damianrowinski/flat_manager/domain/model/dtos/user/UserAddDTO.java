package pl.damianrowinski.flat_manager.domain.model.dtos.user;

import lombok.Data;
import pl.damianrowinski.flat_manager.validation.constraints.CheckPassword;
import pl.damianrowinski.flat_manager.validation.constraints.UniqueEmail;
import pl.damianrowinski.flat_manager.validation.constraints.UniqueLogin;
import pl.damianrowinski.flat_manager.validation.groups.AddressValidationGroup;

import javax.validation.constraints.*;

@Data
@CheckPassword
public class UserAddDTO {

    @NotBlank
    @UniqueLogin
    private String login;

    @NotBlank
    private String password;

    @NotBlank
    private String passwordCheck;

    @Email
    @NotBlank
    @UniqueEmail
    private String email;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

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
