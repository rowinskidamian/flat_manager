package pl.damianrowinski.flat_manager.model.dtos.tenant;

import lombok.Data;

@Data
public class TenantAddressDTO {

    private String firstName;
    private String lastName;

    private String email;

    private String cityName;
    private String streetName;
    private Integer streetNumber;
    private Integer apartmentNumber;

}
