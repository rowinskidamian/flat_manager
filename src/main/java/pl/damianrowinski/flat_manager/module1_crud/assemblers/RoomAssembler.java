package pl.damianrowinski.flat_manager.module1_crud.assemblers;

import org.springframework.stereotype.Component;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.dtos.room.RoomTransferDTO;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.entities.Property;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.entities.Room;

@Component
public class RoomAssembler {
    public RoomTransferDTO convertRoomToTransferData(Room roomToConvert) {
        RoomTransferDTO roomData = new RoomTransferDTO();
        Property property = roomToConvert.getProperty();

        roomData.setPropertyId(property.getId());
        roomData.setPropertyName(property.getWorkingName());

        roomData.setRoomId(roomToConvert.getId());
        roomData.setTenantId(roomToConvert.getTenant().getId());
        roomData.setTenantName(roomToConvert.getTenant().getPersonalDetails().getFullName());

        return roomData;
    }
}
