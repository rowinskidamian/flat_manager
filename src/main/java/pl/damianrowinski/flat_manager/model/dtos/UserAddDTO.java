package pl.damianrowinski.flat_manager.model.dtos;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;
import pl.damianrowinski.flat_manager.validation.constraints.UniqueLogin;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserAddDTO {

    @NotBlank
    @UniqueLogin
    private String login;
    @NotBlank
    private String password;
    @NotBlank
    private String passwordCheck;

    @Email
    private String email;

    private String firstName;
    private String lastName;
    private String cityName;
    private String streetName;
    private Integer streetNumber;
    private Integer apartmentNumber;

}
