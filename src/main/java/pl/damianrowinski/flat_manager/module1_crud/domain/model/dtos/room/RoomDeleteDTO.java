package pl.damianrowinski.flat_manager.module1_crud.domain.model.dtos.room;

import lombok.Data;

@Data
public class RoomDeleteDTO {
    private Long id;
    private Long propertyId;
}
