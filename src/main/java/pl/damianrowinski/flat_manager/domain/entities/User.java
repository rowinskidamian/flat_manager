package pl.damianrowinski.flat_manager.domain.entities;

import lombok.Getter;
import lombok.Setter;
import pl.damianrowinski.flat_manager.model.common.Address;
import pl.damianrowinski.flat_manager.model.common.PersonNameContact;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Getter
@Setter
@Transactional
@Table(name = User.TABLE_NAME)
public class User extends BaseEntity {

    public User() {
        address = new Address();
        nameContact = new PersonNameContact();
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

    private PersonNameContact nameContact;

    private Address address;

    @Override
    public String toString() {
        return "User{ id= " + getId() +
                "login='" + login + '\'' +
                ", active=" + active +
                ", role='" + role + '\'' +
                ", firstName='" + role + '\'' +
                ", lastName='" + role + '\'' +
                getNameContact().toString() + '\'' +
                getAddress().toString() + '\'' +
                "}";
    }
}
