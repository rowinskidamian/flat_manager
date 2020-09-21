package pl.damianrowinski.flat_manager.model.common;

import lombok.Data;

import javax.persistence.*;

@Data
@Embeddable
public class PersonalDetails {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "street_number")
    private Integer streetNumber;

    @Column(name = "apartment_number")
    private Integer apartmentNumber;
}
