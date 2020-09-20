package pl.damianrowinski.flat_manager.domain.entities;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Embeddable

public class PersonalDetails {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    private String email;

    @Column(name = "city_name", nullable = false)
    private String cityName;

    @Column(name = "street_name", nullable = false)
    private String streetName;

    @Column(name = "street_number", nullable = false)
    private Integer streetNumber;

    @Column(name = "apartment_number", nullable = false)
    private Integer apartmentNumber;

}
