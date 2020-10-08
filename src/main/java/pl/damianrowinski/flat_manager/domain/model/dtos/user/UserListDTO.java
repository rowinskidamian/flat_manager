package pl.damianrowinski.flat_manager.domain.model.dtos.user;

import lombok.Data;

@Data

public class UserListDTO {

    private Long id;
    private String login;
    private String firstName;
    private String fullName;

}
