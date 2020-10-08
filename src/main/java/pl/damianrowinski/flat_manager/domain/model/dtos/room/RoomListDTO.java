package pl.damianrowinski.flat_manager.domain.model.dtos.room;

import lombok.Data;

@Data
public class RoomListDTO {

    private Long roomId;
    private String roomNameAndPrice;
}
