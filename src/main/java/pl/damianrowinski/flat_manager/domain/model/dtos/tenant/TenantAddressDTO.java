package pl.damianrowinski.flat_manager.domain.model.dtos.tenant;

import lombok.Data;

@Data
public class TenantAddressDTO {

    private String firstName;
    private String lastName;

    private String email;

    private String cityName;
    private String streetName;
    private String addressNumber;
}
