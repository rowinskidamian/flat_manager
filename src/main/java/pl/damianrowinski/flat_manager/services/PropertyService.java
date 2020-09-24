package pl.damianrowinski.flat_manager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.domain.entities.Property;
import pl.damianrowinski.flat_manager.domain.entities.Room;
import pl.damianrowinski.flat_manager.exceptions.ElementNotFoundException;
import pl.damianrowinski.flat_manager.exceptions.FrobiddenAccessException;
import pl.damianrowinski.flat_manager.exceptions.ObjectInRelationshipException;
import pl.damianrowinski.flat_manager.model.common.Address;
import pl.damianrowinski.flat_manager.model.common.PersonNameContact;
import pl.damianrowinski.flat_manager.model.dtos.PropertyDeleteDTO;
import pl.damianrowinski.flat_manager.model.dtos.PropertyEditDTO;
import pl.damianrowinski.flat_manager.model.dtos.PropertyShowDTO;
import pl.damianrowinski.flat_manager.model.dtos.RoomShowDTO;
import pl.damianrowinski.flat_manager.model.repositories.PropertyRepository;
import pl.damianrowinski.flat_manager.model.repositories.RoomRepository;
import pl.damianrowinski.flat_manager.utils.LoggedUsername;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j

public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    public Property save(PropertyEditDTO propertyEditDTO) {
        Property property = modelMapper.map(propertyEditDTO, Property.class);
        Address address = modelMapper.map(propertyEditDTO, Address.class);
        property.setAddress(address);
        PersonNameContact ownerDetails = modelMapper.map(propertyEditDTO, PersonNameContact.class);
        property.setOwnerDetails(ownerDetails);

        log.info("Attempt to save property: " + property);
        return propertyRepository.save(property);
    }

    public PropertyEditDTO findToEditById(Long id) {

        Optional<Property> optionalProperty = propertyRepository.findById(id);
        if (optionalProperty.isEmpty())
            throw new ElementNotFoundException("Nie znalazłem mieszkania o podanym id.");

        Property property = optionalProperty.get();
        String loggedUserName = property.getLoggedUserName();

        if (!loggedUserName.equals(LoggedUsername.get()))
            throw new FrobiddenAccessException("Nie masz dostępu do tych danych.");

        PropertyEditDTO propertyToEditData = modelMapper.map(property, PropertyEditDTO.class);
        Address address = property.getAddress();
        propertyToEditData.setCityName(address.getCityName());
        propertyToEditData.setStreetName(address.getStreetName());
        propertyToEditData.setStreetNumber(address.getStreetNumber());
        propertyToEditData.setApartmentNumber(address.getApartmentNumber());

        PersonNameContact ownerDetails = property.getOwnerDetails();
        propertyToEditData.setFirstName(ownerDetails.getFirstName());
        propertyToEditData.setLastName(ownerDetails.getLastName());
        propertyToEditData.setEmail(ownerDetails.getEmail());

        return propertyToEditData;
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
                roomData.setTenantFullName((room.getTenant().getPersonalDetails().getFullName()));
            }
            roomsToShowList.add(roomData);
        }

        propertyData.setRooms(roomsToShowList);
        propertyData.setRoomsNumber(propertyRooms.size());

        log.info("Object loaded from database: " + propertyData);

        return propertyData;
    }

    public List<PropertyShowDTO> findAllByUser(String loggedUsername) {

        List<PropertyShowDTO> listOfPropertiesToShow = new ArrayList<>();

        List<Property> propertyList = propertyRepository.findAllByLoggedUserName(loggedUsername);

        for (Property property : propertyList) {
            PropertyShowDTO propertyToShowData = modelMapper.map(property, PropertyShowDTO.class);

            PersonNameContact ownerDetails = property.getOwnerDetails();
            propertyToShowData.setOwnerName(ownerDetails.getFullName());
            propertyToShowData.setEmail(ownerDetails.getEmail());

            Address address = property.getAddress();
            propertyToShowData.setCityName(address.getCityName());
            propertyToShowData.setStreetName(address.getStreetName());
            propertyToShowData.setAddressFullNumber(address.getCombinedAddressNumber());

            List<Room> allRoomsFromProperty = roomRepository.findAllByPropertyId(property.getId());

            propertyToShowData.setRoomsNumber(allRoomsFromProperty.size());
            listOfPropertiesToShow.add(propertyToShowData);
        }

        return listOfPropertiesToShow;

    }

    public PropertyDeleteDTO findPropertyToDelete(Long propertyId) {

        Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
        if (optionalProperty.isEmpty()) throw new ElementNotFoundException("Brak mieszkania o podanym id");

        List<Room> propertyRoomsList = roomRepository.findAllByPropertyId(propertyId);

        for (Room room : propertyRoomsList) {
            if (room.getTenant() != null)
                throw new ObjectInRelationshipException("Mieszkanie ma najemców, opróżnij mieszkanie, przed usunięciem");
        }

        Property property = optionalProperty.get();
        String loggedUserName = property.getLoggedUserName();

        if (!loggedUserName.equals(LoggedUsername.get()))
            throw new FrobiddenAccessException("Nie możesz usunąć obiektu, który nie należy do Twojego konta");

        PropertyDeleteDTO propertyDeleteDTO = new PropertyDeleteDTO();
        propertyDeleteDTO.setPropertyId(propertyId);
        propertyDeleteDTO.setLoggedUserName(loggedUserName);

        return propertyDeleteDTO;
    }

    public void delete(PropertyDeleteDTO propertyDeleteDTO) {

        Optional<Property> optionalProperty = propertyRepository.findById(propertyDeleteDTO.getPropertyId());
        if (optionalProperty.isEmpty()) throw new ElementNotFoundException("Brak mieszkania o podanym id");

        String loggedUserName = propertyDeleteDTO.getLoggedUserName();
        if (!loggedUserName.equals(LoggedUsername.get()))
            throw new FrobiddenAccessException("Nie możesz usunąć obiektu, który nie należy do Twojego konta");

        log.info("Deleting property with id:" + propertyDeleteDTO.getPropertyId());

        List<Room> propertyRooms = roomRepository.findAllByPropertyId(propertyDeleteDTO.getPropertyId());
        propertyRooms.forEach(room -> roomRepository.delete(room));

        propertyRepository.delete(optionalProperty.get());
    }
}
