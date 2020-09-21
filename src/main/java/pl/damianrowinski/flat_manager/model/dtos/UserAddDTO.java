package pl.damianrowinski.flat_manager.model.dtos;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.Email;

@Data
public class UserAddDTO {

    @NotNull
    private String login;
    @NotNull
    private String password;
    @NotNull
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
