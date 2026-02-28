package pl.damianrowinski.flat_manager.domain.model.common;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Embeddable
public class Address {
    @Column(name = "city_name")
    private String cityName;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "street_number")
    private Integer streetNumber;

    @Column(name = "apartment_number")
    private Integer apartmentNumber;

    public String getCombinedAddressNumber() {
        if (apartmentNumber != null) {
            return streetNumber + "/" + apartmentNumber;
        } else {
            return "" + streetNumber;
        }
    }
}
