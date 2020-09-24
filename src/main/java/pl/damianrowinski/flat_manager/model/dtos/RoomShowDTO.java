package pl.damianrowinski.flat_manager.model.dtos;

import lombok.Data;

@Data
public class RoomShowDTO {

    private Long id;
    private Double catalogRent;
    private Long propertyId;
    private Long tenantId;
    private String tenantFullName;

}
