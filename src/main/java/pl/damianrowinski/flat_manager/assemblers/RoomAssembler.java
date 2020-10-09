package pl.damianrowinski.flat_manager.assemblers;

import org.springframework.stereotype.Component;
import pl.damianrowinski.flat_manager.domain.model.dtos.room.RoomTransferDTO;
import pl.damianrowinski.flat_manager.domain.model.entities.Property;
import pl.damianrowinski.flat_manager.domain.model.entities.Room;

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

        roomData.setCatalogRent(roomToConvert.getCatalogRent());

        return roomData;
    }
}
