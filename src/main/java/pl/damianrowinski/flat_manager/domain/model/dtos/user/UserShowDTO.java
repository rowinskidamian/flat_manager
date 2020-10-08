package pl.damianrowinski.flat_manager.domain.model.dtos.user;

import lombok.Data;

@Data
public class UserShowDTO {

    private String login;
    private String email;

    private String firstName;
    private String lastName;

    private String cityName;
    private String streetName;

    private Integer streetNumber;
    private Integer apartmentNumber;

}
