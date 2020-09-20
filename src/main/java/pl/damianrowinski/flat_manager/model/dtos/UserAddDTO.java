package pl.damianrowinski.flat_manager.model.dtos;

import lombok.Data;

@Data
public class UserAddDTO {

    private String login;
    private String password;
    private String passwordCheck;
    private String email;

    private String firstName;
    private String lastName;
    private String cityName;
    private String streetName;
    private Integer streetNumber;
    private Integer apartmentNumber;

}
