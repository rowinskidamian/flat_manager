package pl.damianrowinski.flat_manager.domain.entities;

import lombok.Getter;
import lombok.Setter;
import pl.damianrowinski.flat_manager.domain.entities.PersonalDetails;
import pl.damianrowinski.flat_manager.security.Role;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    private PersonalDetails personalDetails;
    private String companyName;
    private long NIP;
    private String login;
    private String password;
    private int enabled;

    @ManyToOne
    private Role role;
}
