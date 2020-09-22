package pl.damianrowinski.flat_manager.domain.entities;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import pl.damianrowinski.flat_manager.model.common.PersonalDetails;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Getter
@Setter
@Transactional
@Table(name = User.TABLE_NAME)
public class User extends BaseEntity {

    public User() {
        personalDetails = new PersonalDetails();
    }

    final static String TABLE_NAME = "users";

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    private String role;

    private PersonalDetails personalDetails;

    @Override
    public String toString() {
        return "User{ id= " + getId() +
                "login='" + login + '\'' +
                ", active=" + active +
                ", role='" + role + '\'' +
                "}";
    }
}
