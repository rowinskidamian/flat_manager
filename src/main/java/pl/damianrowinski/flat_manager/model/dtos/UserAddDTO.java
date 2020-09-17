package pl.damianrowinski.flat_manager.model.dtos;

import lombok.Data;

@Data
public class UserAddDTO {

    private String login;
    private String password;
    private String firstName;
    private String lastName;
}
