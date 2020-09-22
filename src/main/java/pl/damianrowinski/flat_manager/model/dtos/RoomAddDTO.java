package pl.damianrowinski.flat_manager.model.dtos;

import lombok.Data;
import pl.damianrowinski.flat_manager.domain.entities.Property;
import pl.damianrowinski.flat_manager.domain.entities.Tenant;

@Data
public class RoomAddDTO {

    private Double catalogRent;
    private Property property;
    private Tenant tenant;

}
