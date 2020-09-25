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
import pl.damianrowinski.flat_manager.model.dtos.*;
import pl.damianrowinski.flat_manager.model.repositories.PropertyRepository;
import pl.damianrowinski.flat_manager.model.repositories.RoomRepository;
import pl.damianrowinski.flat_manager.utils.LoggedUsername;

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
        addAddressToEditedPropertyData(propertyToEditData, address);

        PersonNameContact ownerDetails = property.getOwnerDetails();
        addOwnerToEditedPropertyData(propertyToEditData, ownerDetails);

        return propertyToEditData;
    }

    private void addOwnerToEditedPropertyData(PropertyEditDTO propertyToEditData, PersonNameContact ownerDetails) {
        propertyToEditData.setFirstName(ownerDetails.getFirstName());
        propertyToEditData.setLastName(ownerDetails.getLastName());
        propertyToEditData.setEmail(ownerDetails.getEmail());
    }

    private void addAddressToEditedPropertyData(PropertyEditDTO propertyToEditData, Address address) {
        propertyToEditData.setCityName(address.getCityName());
        propertyToEditData.setStreetName(address.getStreetName());
        propertyToEditData.setStreetNumber(address.getStreetNumber());
        propertyToEditData.setApartmentNumber(address.getApartmentNumber());
    }


    public PropertyShowDTO findByIdWithRooms(Long id) {
        Optional<Property> optionalProperty = propertyRepository.findById(id);
        if (optionalProperty.isEmpty())
            throw new ElementNotFoundException("Nie znalazłem mieszkania o podanym id.");

        Property property = optionalProperty.get();
        PropertyShowDTO propertyData = modelMapper.map(property, PropertyShowDTO.class);

        addOwnerToPropertyData(property, propertyData);
        addAddressToPropertyData(property, propertyData);

        List<Room> propertyRooms = roomRepository.findAllByPropertyId(id);
        List<RoomShowDTO> roomsToShowList = new ArrayList<>();

        convertRoomsToRoomsData(propertyRooms, roomsToShowList);

        propertyData.setRooms(roomsToShowList);
        propertyData.setRoomsNumber(propertyRooms.size());

        log.info("Object loaded from database: " + propertyData);

        return propertyData;
    }

    private void convertRoomsToRoomsData(List<Room> propertyRooms, List<RoomShowDTO> roomsToShowList) {
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
    }

    public List<PropertyListDTO> findAllByUserShowList(String loggedUsername) {
        List<Property> propertyList = propertyRepository.findAllByLoggedUserName(loggedUsername);
        List<PropertyListDTO> propertiesData = new ArrayList<>();

        for (Property property : propertyList) {
            PropertyListDTO propertyData = new PropertyListDTO();
            propertyData.setLoggedUserName(property.getLoggedUserName());
            propertyData.setPropertyId(property.getId());
            propertyData.setPropertyWorkingName(property.getWorkingName());
            propertiesData.add(propertyData);
        }
        return propertiesData;
    }

    public List<PropertyShowDTO> findAllByUser(String loggedUsername) {
        List<Property> propertyList = propertyRepository.findAllByLoggedUserName(loggedUsername);
        List<PropertyShowDTO> listOfPropertiesToShow = new ArrayList<>();

        convertPropertyToPropertyData(propertyList, listOfPropertiesToShow);

        return listOfPropertiesToShow;
    }

    private void convertPropertyToPropertyData(List<Property> propertyList, List<PropertyShowDTO> listOfPropertiesToShow) {
        for (Property property : propertyList) {
            PropertyShowDTO propertyToShowData = modelMapper.map(property, PropertyShowDTO.class);

            addOwnerToPropertyData(property, propertyToShowData);
            addAddressToPropertyData(property, propertyToShowData);

            List<Room> allRoomsFromProperty = roomRepository.findAllByPropertyId(property.getId());

            propertyToShowData.setRoomsNumber(allRoomsFromProperty.size());
            listOfPropertiesToShow.add(propertyToShowData);
        }
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
        propertyRooms.forEach(roomRepository::delete);

        propertyRepository.delete(optionalProperty.get());
    }

    private void addOwnerToPropertyData(Property property, PropertyShowDTO propertyToShowData) {
        PersonNameContact ownerDetails = property.getOwnerDetails();
        propertyToShowData.setOwnerName(ownerDetails.getFullName());
        propertyToShowData.setEmail(ownerDetails.getEmail());
    }

    private void addAddressToPropertyData(Property propertyToShow, PropertyShowDTO propertyData) {
        Address propertyAddress = propertyToShow.getAddress();
        propertyData.setCityName(propertyAddress.getCityName());
        propertyData.setStreetName(propertyAddress.getStreetName());
        propertyData.setAddressFullNumber(propertyAddress.getCombinedAddressNumber());
    }


}
