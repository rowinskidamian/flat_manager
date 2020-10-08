package pl.damianrowinski.flat_manager.domain.model.dtos.room;

import lombok.Data;

@Data
public class RoomDeleteDTO {
    private Long id;
    private Long propertyId;
}
