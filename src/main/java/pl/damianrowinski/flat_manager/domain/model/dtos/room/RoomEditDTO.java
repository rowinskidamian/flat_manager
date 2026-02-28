package pl.damianrowinski.flat_manager.domain.model.dtos.room;

import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

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
