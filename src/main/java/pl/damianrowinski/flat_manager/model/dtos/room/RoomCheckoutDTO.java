package pl.damianrowinski.flat_manager.model.dtos.room;

import lombok.Data;

@Data
public class RoomCheckoutDTO {
    private Long roomId;
    private Long tenantId;
}
