package pl.damianrowinski.flat_manager.user;

import pl.damianrowinski.flat_manager.personal_details.PersonalDetails;

import javax.persistence.*;

@Entity
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
}
