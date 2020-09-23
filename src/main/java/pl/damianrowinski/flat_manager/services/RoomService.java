package pl.damianrowinski.flat_manager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.domain.entities.Property;
import pl.damianrowinski.flat_manager.domain.entities.Room;
import pl.damianrowinski.flat_manager.exceptions.ElementNotFoundException;
import pl.damianrowinski.flat_manager.model.dtos.RoomAddDTO;
import pl.damianrowinski.flat_manager.model.repositories.PropertyRepository;
import pl.damianrowinski.flat_manager.model.repositories.RoomRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor

public class RoomService {

    private final RoomRepository roomRepository;
    private final PropertyRepository propertyRepository;

    public List<Room> findAllByPropertyId(Long id) {
        List<Room> roomsByPropertyId = roomRepository.findAllByPropertyId(id);
        log.info("For apartment id: " + id + ", found no of rooms: " + roomsByPropertyId.size());
        return roomsByPropertyId;
    }

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
}
