package pl.damianrowinski.flat_manager.model.dtos.room;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class RoomEditDTO {

    @NotNull
    @Min(value = 1)
    private Double catalogRent;
    @NotNull
    private Long propertyId;
    private Long tenantId;
    private String tenantFullName;

}
