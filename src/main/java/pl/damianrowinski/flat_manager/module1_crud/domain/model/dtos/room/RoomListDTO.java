package pl.damianrowinski.flat_manager.module1_crud.domain.model.dtos.room;

import lombok.Data;

@Data
public class RoomListDTO {

    private Long roomId;
    private String roomNameAndPrice;
}
