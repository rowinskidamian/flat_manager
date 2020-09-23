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

    public void save(RoomAddDTO roomToAdd) {
        Room room = new Room();
        room.setCatalogRent(roomToAdd.getCatalogRent());
        room.setProperty(roomToAdd.getProperty());
        room.setTenant(roomToAdd.getTenant());
        log.info("Adding room to apartment: " + room.getProperty().getWorkingName() + " room: " + room);
        roomRepository.save(room);
    }

    public List<Room> findAllByPropertyId(Long id) {
        List<Room> roomsByPropertyId = roomRepository.findAllByPropertyId(id);
        log.info("For apartment id: " + id + ", found no of rooms: " + roomsByPropertyId.size());
        return roomsByPropertyId;
    }

    public void addNewToProperty(Double rent, Long propertyId) {
        Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
        if (optionalProperty.isEmpty())
            throw new ElementNotFoundException("Nie znalazłem mieszkania o podanym id.");
        Room room = new Room();
        room.setCatalogRent(rent);
        room.setProperty(optionalProperty.get());
        log.info("Adding room to apartment: " + room.getProperty().getWorkingName() + " room: " + room);
        roomRepository.save(room);
    }
}
