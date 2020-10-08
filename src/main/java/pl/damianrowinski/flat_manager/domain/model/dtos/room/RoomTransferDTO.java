package pl.damianrowinski.flat_manager.domain.model.dtos.room;

import lombok.Data;

@Data
public class RoomTransferDTO {
    private Long roomId;
    private Long tenantId;
    private String tenantName;
    private Long propertyId;
    private String propertyName;
}
