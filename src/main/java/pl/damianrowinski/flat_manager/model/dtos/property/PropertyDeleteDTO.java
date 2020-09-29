package pl.damianrowinski.flat_manager.model.dtos.property;

import lombok.Data;

import java.util.List;

@Data
public class PropertyDeleteDTO {
    private Long propertyId;
    private String loggedUserName;
}
