package pl.damianrowinski.flat_manager.model.dtos;

import lombok.Data;
import pl.damianrowinski.flat_manager.domain.entities.Tenant;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class RoomAddDTO {

    @NotNull
    @Min(value = 1)
    private Double catalogRent;
    private Long propertyId;
    private Tenant tenant;

}
