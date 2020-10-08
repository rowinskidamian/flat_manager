package pl.damianrowinski.flat_manager.domain.model.dtos.property;

import lombok.Data;

@Data
public class PropertyListDTO {
    private Long propertyId;
    private String propertyWorkingName;
    private String loggedUserName;
}
