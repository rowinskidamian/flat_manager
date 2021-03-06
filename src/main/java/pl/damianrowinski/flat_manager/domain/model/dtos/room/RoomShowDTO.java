package pl.damianrowinski.flat_manager.domain.model.dtos.room;

import lombok.Data;

@Data
public class RoomShowDTO {

    private Long id;
    private Double catalogRent;
    private Long propertyId;
    private Long tenantId;
    private String tenantFullName;
    private String apartmentWorkingName;

}
