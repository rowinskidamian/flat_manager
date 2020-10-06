package pl.damianrowinski.flat_manager.module1_crud.domain.model.dtos.property;

import lombok.Data;

@Data
public class PropertyListDTO {
    private Long propertyId;
    private String propertyWorkingName;
    private String loggedUserName;
}
