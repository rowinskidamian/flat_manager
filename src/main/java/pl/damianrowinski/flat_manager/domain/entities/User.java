package pl.damianrowinski.flat_manager.domain.entities;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import pl.damianrowinski.flat_manager.domain.entities.PersonalDetails;
import pl.damianrowinski.flat_manager.security.Role;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Getter
@Setter
@Transactional
@Table(name = User.TABLE_NAME)
public class User {
    final static String TABLE_NAME = "users";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private PersonalDetails personalDetails;

    private String companyName;
    private long NIP;

    @NotNull
    private String login;

    @NotNull
    private String password;

    @Column(nullable = false)
    private Boolean active;

    @ManyToOne
    private Role role;
}
