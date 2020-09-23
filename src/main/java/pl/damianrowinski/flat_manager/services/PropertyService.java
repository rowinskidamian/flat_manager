package pl.damianrowinski.flat_manager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.domain.entities.Property;
import pl.damianrowinski.flat_manager.domain.entities.Room;
import pl.damianrowinski.flat_manager.exceptions.ElementNotFoundException;
import pl.damianrowinski.flat_manager.model.common.Address;
import pl.damianrowinski.flat_manager.model.common.PersonNameContact;
import pl.damianrowinski.flat_manager.model.dtos.PropertyEditDTO;
import pl.damianrowinski.flat_manager.model.dtos.PropertyShowDTO;
import pl.damianrowinski.flat_manager.model.dtos.RoomShowDTO;
import pl.damianrowinski.flat_manager.model.repositories.PropertyRepository;
import pl.damianrowinski.flat_manager.model.repositories.RoomRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j

public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    public Property save(PropertyEditDTO propertyAddDTO) {
        Property property = modelMapper.map(propertyAddDTO, Property.class);
        Address address = modelMapper.map(propertyAddDTO, Address.class);
        property.setAddress(address);
        PersonNameContact ownerDetails = modelMapper.map(propertyAddDTO, PersonNameContact.class);
        property.setOwnerDetails(ownerDetails);

        log.info("Attempt to save property: " + property);
        return propertyRepository.save(property);
    }

    public PropertyShowDTO findByIdWithRooms(Long id) {
        Optional<Property> optionalProperty = propertyRepository.findById(id);
        if (optionalProperty.isEmpty())
            throw new ElementNotFoundException("Nie znalazłem mieszkania o podanym id.");

        Property propertyToShow = optionalProperty.get();
        PropertyShowDTO propertyData = modelMapper.map(propertyToShow, PropertyShowDTO.class);

        PersonNameContact ownerDetails = propertyToShow.getOwnerDetails();
        propertyData.setOwnerName(ownerDetails.getFullName());
        propertyData.setEmail(ownerDetails.getEmail());

        Address propertyAddress = propertyToShow.getAddress();

        propertyData.setCityName(propertyAddress.getCityName());
        propertyData.setStreetName(propertyAddress.getStreetName());
        propertyData.setAddressFullNumber(propertyAddress.getCombinedAddressNumber());

        List<Room> propertyRooms = roomRepository.findAllByPropertyId(id);
        List<RoomShowDTO> roomsToShowList = new ArrayList<>();

        for (Room room : propertyRooms) {
            RoomShowDTO roomData = new RoomShowDTO();
            roomData.setId(room.getId());
            roomData.setCatalogRent(room.getCatalogRent());
            roomData.setPropertyId(room.getProperty().getId());
            if (room.getTenant() != null) {
                roomData.setTenantId(room.getTenant().getId());
                roomData.setTenatFullName(room.getTenant().getPersonalDetails().getFullName());
            }
            roomsToShowList.add(roomData);
        }

        propertyData.setRooms(roomsToShowList);
        propertyData.setRoomsNumber(propertyRooms.size());

        log.info("Object loaded from database: " + propertyData);

        return propertyData;
    }

    public Optional<Property> findById(Long id) {
        return propertyRepository.findById(id);
    }

}
