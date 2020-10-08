package pl.damianrowinski.flat_manager.domain.model.dtos.property;

import lombok.Data;

@Data
public class PropertyDeleteDTO {
    private Long propertyId;
    private String loggedUserName;
}
