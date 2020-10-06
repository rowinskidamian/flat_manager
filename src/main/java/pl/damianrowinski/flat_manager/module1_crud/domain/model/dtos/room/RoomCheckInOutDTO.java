package pl.damianrowinski.flat_manager.module1_crud.domain.model.dtos.room;

import lombok.Data;

@Data
public class RoomCheckInOutDTO {
    private Long roomId;
    private Long tenantId;
    private Long propertyId;
}
