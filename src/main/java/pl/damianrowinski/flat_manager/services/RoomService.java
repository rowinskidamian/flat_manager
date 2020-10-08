package pl.damianrowinski.flat_manager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.assemblers.RoomAssembler;
import pl.damianrowinski.flat_manager.domain.model.entities.Property;
import pl.damianrowinski.flat_manager.domain.model.entities.Room;
import pl.damianrowinski.flat_manager.domain.model.entities.Tenant;
import pl.damianrowinski.flat_manager.exceptions.ElementNotFoundException;
import pl.damianrowinski.flat_manager.exceptions.ForbiddenAccessException;
import pl.damianrowinski.flat_manager.exceptions.ObjectInRelationshipException;
import pl.damianrowinski.flat_manager.domain.model.dtos.room.*;
import pl.damianrowinski.flat_manager.domain.repositories.PropertyRepository;
import pl.damianrowinski.flat_manager.domain.repositories.RoomRepository;
import pl.damianrowinski.flat_manager.domain.repositories.TenantRepository;
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
    private final TenantRepository tenantRepository;
    private final RoomAssembler roomAssembler;

    public RoomTransferDTO findByTenantId(Long tenantId) {
        Optional<Room> optionalRoom = roomRepository.findFirstByTenantId(tenantId);
        if (optionalRoom.isEmpty()) throw new ElementNotFoundException("Brak pokoju dla najemcy o podanym id");

        Room roomWithTenant = optionalRoom.get();
        return roomAssembler.convertRoomToTransferData(roomWithTenant);
    }

    public void save(RoomEditDTO roomData) {
        log.info("Attempt to save room:");
        log.info(roomData.toString());

        Room roomToSave = new Room();

        Long tenantId = roomData.getTenantId();
        if (tenantId != null) {
            Optional<Tenant> optionalTenant = tenantRepository.findById(tenantId);
            if (optionalTenant.isEmpty()) throw new ElementNotFoundException("Nie znalazłem najemcy o podanym id");
            roomToSave.setTenant(optionalTenant.get());
        }

        Long propertyId = roomData.getPropertyId();
        if (propertyId != null) {
            Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
            if (optionalProperty.isEmpty()) throw new ElementNotFoundException("Nie znalazłem mieszkania o podanym id");
            roomToSave.setProperty(optionalProperty.get());
        }

        roomToSave.setCatalogRent(roomData.getCatalogRent());
        roomRepository.save(roomToSave);

    }

    public void addNewToProperty(RoomEditDTO roomData) {
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
            throw new ForbiddenAccessException("Dostęp do zasobu zabroniony");
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
        List<Room> roomList = roomRepository.findAllByLoggedUserNameOrderByPropertyWorkingNameAsc(loggedUserName);
        List<RoomShowDTO> roomToShowList = new ArrayList<>();

        for (Room room : roomList) {
            RoomShowDTO roomData = new RoomShowDTO();

            roomData.setId(room.getId());
            roomData.setCatalogRent(room.getCatalogRent());

            Property property = room.getProperty();
            roomData.setPropertyId(property.getId());
            roomData.setApartmentWorkingName(property.getWorkingName());

            Tenant tenant = room.getTenant();
            if (tenant != null) {
                roomData.setTenantId(tenant.getId());
                roomData.setTenantFullName(tenant.getPersonalDetails().getFullName());
            }
            roomToShowList.add(roomData);
        }
        return roomToShowList;
    }

    public RoomEditDTO findRoomToEdit(Long roomId) {

        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        if (optionalRoom.isEmpty()) throw new ElementNotFoundException("Nie znalazłem pokoju o podanym id.");

        Room roomToEdit = optionalRoom.get();

        if (!roomToEdit.getLoggedUserName().equals(LoggedUsername.get()))
            throw new ForbiddenAccessException("Dostęp do zasobu zabroniony");

        RoomEditDTO roomData = new RoomEditDTO();

        roomData.setCatalogRent(roomToEdit.getCatalogRent());
        roomData.setPropertyId(roomToEdit.getProperty().getId());

        Tenant tenant = roomToEdit.getTenant();

        if (tenant != null) {
            roomData.setTenantId(tenant.getId());
            roomData.setTenantFullName(tenant.getPersonalDetails().getFullName());
        }

        return roomData;

    }

    public List<RoomListDTO> findAllAvailableRooms(String loggedUserName) {
        List<Room> roomsList = roomRepository.findAllByLoggedUserNameAndTenantIsNull(loggedUserName);

        List<RoomListDTO> roomsDataList = new ArrayList<>();

        for (Room room : roomsList) {
            RoomListDTO roomDataAddToList = new RoomListDTO();
            roomDataAddToList.setRoomId(room.getId());

            String roomPropertyName = room.getProperty().getWorkingName();
            Double roomRent = room.getCatalogRent();
            String roomNameAndPrice = roomPropertyName + " za " + roomRent;
            roomDataAddToList.setRoomNameAndPrice(roomNameAndPrice);

            roomsDataList.add(roomDataAddToList);
        }
        return roomsDataList;
    }

    public RoomCheckInOutDTO findRoomToCheckout(Long roomId) {
        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        if (optionalRoom.isEmpty()) throw new ElementNotFoundException("Nie ma pokoju o podanym id.");
        Room room = optionalRoom.get();
        if (!room.getLoggedUserName().equals(LoggedUsername.get())) throw new ForbiddenAccessException("Brak dostępu.");
        RoomCheckInOutDTO roomCheckoutData = new RoomCheckInOutDTO();
        roomCheckoutData.setRoomId(room.getId());
        if (room.getTenant() != null)
            roomCheckoutData.setTenantId(room.getTenant().getId());
        roomCheckoutData.setPropertyId(room.getProperty().getId());
        return roomCheckoutData;
    }

    public void checkout(Long roomId) {
        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        if (optionalRoom.isEmpty()) throw new ElementNotFoundException("Nie ma pokoju o podanym id.");
        Room room = optionalRoom.get();
        if (!room.getLoggedUserName().equals(LoggedUsername.get())) throw new ForbiddenAccessException("Brak dostępu.");
        log.info("Checking out tenant from room with id:" + room.getId());

        room.setTenant(null);
        roomRepository.save(room);
    }

    public void checkInTenant(Long tenantId, Long roomId) {
        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        if (optionalRoom.isEmpty()) throw new ElementNotFoundException("Brak pokoju o podanym id.");
        Room room = optionalRoom.get();
        if (!room.getLoggedUserName().equals(LoggedUsername.get()))
            throw new ForbiddenAccessException("Brak dostępu do zasobu");

        Optional<Tenant> optionalTenant = tenantRepository.findById(tenantId);
        if (optionalTenant.isEmpty()) throw new ElementNotFoundException("Brak najemcy o podanym id.");
        Tenant tenant = optionalTenant.get();
        if (!tenant.getLoggedUserName().equals(LoggedUsername.get()))
            throw new ForbiddenAccessException("Brak dostępu.");

        room.setTenant(tenant);
        log.info("Checked in tenant: " + tenant.getPersonalDetails().getFullName() + ", to apartment: "
                + room.getProperty().getWorkingName());

        roomRepository.save(room);
    }
}
