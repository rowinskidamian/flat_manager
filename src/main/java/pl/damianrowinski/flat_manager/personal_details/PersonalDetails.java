package pl.damianrowinski.flat_manager.personal_details;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PersonalDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstName;
    private String lastName;
    private long PESEL;
    private String streetName;
    private int streetNumber;
    private String country;
    private long phoneNumber;
    private String email;

}
