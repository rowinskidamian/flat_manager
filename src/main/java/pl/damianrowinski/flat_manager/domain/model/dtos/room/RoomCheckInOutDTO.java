package pl.damianrowinski.flat_manager.domain.model.dtos.room;

import lombok.Data;

@Data
public class RoomCheckInOutDTO {
    private Long roomId;
    private Long tenantId;
    private Long propertyId;
}
