package pl.damianrowinski.flat_manager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.domain.entities.Property;
import pl.damianrowinski.flat_manager.domain.entities.Room;
import pl.damianrowinski.flat_manager.domain.entities.Tenant;
import pl.damianrowinski.flat_manager.exceptions.ElementNotFoundException;
import pl.damianrowinski.flat_manager.exceptions.FrobiddenAccessException;
import pl.damianrowinski.flat_manager.exceptions.ObjectInRelationshipException;
import pl.damianrowinski.flat_manager.model.dtos.PropertyShowDTO;
import pl.damianrowinski.flat_manager.model.dtos.RoomAddDTO;
import pl.damianrowinski.flat_manager.model.dtos.RoomDeleteDTO;
import pl.damianrowinski.flat_manager.model.dtos.RoomShowDTO;
import pl.damianrowinski.flat_manager.model.repositories.PropertyRepository;
import pl.damianrowinski.flat_manager.model.repositories.RoomRepository;
import pl.damianrowinski.flat_manager.utils.LoggedUsername;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor

public class RoomService {

    private final RoomRepository roomRepository;
    private final PropertyRepository propertyRepository;


    public void addNewToProperty(RoomAddDTO roomData) {
        Optional<Property> optionalProperty = propertyRepository.findById(roomData.getPropertyId());
        if (optionalProperty.isEmpty())
            throw new ElementNotFoundException("Nie znalazłem mieszkania o podanym id.");
        Room room = new Room();
        room.setCatalogRent(roomData.getCatalogRent());
        room.setProperty(optionalProperty.get());
        log.info("Adding room to apartment: " + room.getProperty().getWorkingName() + " room: " + room);
        roomRepository.save(room);
    }

    public RoomDeleteDTO findRoomToDelete(Long roomId) {
        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        if (optionalRoom.isEmpty()) throw new ElementNotFoundException("Nie znalazłem pokoju o podanym id.");

        Room roomToDelete = optionalRoom.get();

        if (!roomToDelete.getLoggedUserName().equals(LoggedUsername.get()))
            throw new FrobiddenAccessException("Dostęp do zasobu zabroniony");
        if (roomToDelete.getTenant() != null)
            throw new ObjectInRelationshipException("Pokój ma najemcę, najpierw usuń najemcę, a później pokój.");

        RoomDeleteDTO roomData = new RoomDeleteDTO();
        roomData.setId(roomToDelete.getId());
        roomData.setPropertyId(roomToDelete.getProperty().getId());
        return roomData;
    }

    public void delete(Long roomId) {
        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        if (optionalRoom.isEmpty()) throw new ElementNotFoundException("Nie znalazłem pokoju o podanym id.");
        Room roomToDelete = optionalRoom.get();
        if (roomToDelete.getTenant() != null)
            throw new ObjectInRelationshipException("Pokój ma najemcę, najpierw usuń najemcę, a później pokój.");
        log.info("Deleting room with id: " + roomToDelete.getId());
        roomRepository.delete(roomToDelete);
    }

    public List<RoomShowDTO> findAllByUser(String loggedUserName) {
        List<Room> roomList = roomRepository.findAllByLoggedUserName(loggedUserName);
        List<RoomShowDTO> roomToShowList = new ArrayList<>();

        for (Room room : roomList) {
            RoomShowDTO roomData = new RoomShowDTO();

            roomData.setId(room.getId());
            roomData.setCatalogRent(room.getCatalogRent());
            roomData.setPropertyId(room.getProperty().getId());
            roomData.setApartmentWorkingName(room.getProperty().getWorkingName());
            Tenant tenant = room.getTenant();
            if (tenant != null) {
                roomData.setTenantId(tenant.getId());
                roomData.setTenantFullName(tenant.getPersonalDetails().getFullName());
            }
            roomToShowList.add(roomData);
        }
        return roomToShowList;
    }

}
