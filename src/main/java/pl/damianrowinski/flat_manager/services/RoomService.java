package pl.damianrowinski.flat_manager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.domain.entities.Property;
import pl.damianrowinski.flat_manager.domain.entities.Room;
import pl.damianrowinski.flat_manager.domain.entities.Tenant;
import pl.damianrowinski.flat_manager.model.dtos.RoomAddDTO;
import pl.damianrowinski.flat_manager.model.repositories.PropertyRepository;
import pl.damianrowinski.flat_manager.model.repositories.RoomRepository;
import pl.damianrowinski.flat_manager.model.repositories.TenantRepository;

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

    public void addRoomsToProperty(List<RoomAddDTO> listOfRoomsToAdd) {

        List<Room> roomsList = new ArrayList<>();

        for (RoomAddDTO roomDTO : listOfRoomsToAdd) {
            Room room = new Room();
            room.setCatalogRent(roomDTO.getCatalogRent());
            room.setProperty(roomDTO.getProperty());
            room.setTenant(roomDTO.getTenant());
            roomsList.add(room);
        }

        roomsList.stream()
                .forEach(room -> {
                    roomRepository.save(room);
                    log.info("Adding room to apartment: " + room.getProperty().getWorkingName() + " room: " + room);
                });
    }

}
