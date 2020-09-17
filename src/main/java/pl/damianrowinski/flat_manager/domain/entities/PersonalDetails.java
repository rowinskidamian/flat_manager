package pl.damianrowinski.flat_manager.domain.entities;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.transaction.Transactional;

@Entity
@Getter
@Setter
@Transactional

public class PersonalDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private long PESEL;
    private String streetName;
    private int streetNumber;
    private String country;
    private long phoneNumber;
    private String email;

}
