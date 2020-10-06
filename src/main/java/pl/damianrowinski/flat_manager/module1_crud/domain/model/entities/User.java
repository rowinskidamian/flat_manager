package pl.damianrowinski.flat_manager.module1_crud.domain.model.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.common.Address;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.common.PersonNameContact;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Getter
@Setter
@Transactional
@Table(name = User.TABLE_NAME)
@SQLDelete(sql = User.SQL_UPDATE, check = ResultCheckStyle.COUNT)
@Where(clause = "state <> 'DELETED'")
public class User extends BaseEntity {

    final static String TABLE_NAME = "users";
    final static String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET state = 'DELETED' WHERE id = ?";

    public User() {
        address = new Address();
        nameContact = new PersonNameContact();
    }

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
                ", login='" + login + '\'' +
                ", active=" + active +
                ", role='" + role + '\'' +
                ", " + getNameContact().toString() + '\'' +
                ", " + getAddress().toString() + '\'' +
                "}";
    }
}
