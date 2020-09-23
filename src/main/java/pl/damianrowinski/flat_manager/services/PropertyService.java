package pl.damianrowinski.flat_manager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.domain.entities.Property;
import pl.damianrowinski.flat_manager.exceptions.ElementNotFoundException;
import pl.damianrowinski.flat_manager.model.common.Address;
import pl.damianrowinski.flat_manager.model.common.PersonNameContact;
import pl.damianrowinski.flat_manager.model.dtos.PropertyDTO;
import pl.damianrowinski.flat_manager.model.repositories.PropertyRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j

public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final ModelMapper modelMapper;

    public Property save(PropertyDTO propertyAddDTO) {
        Property property = modelMapper.map(propertyAddDTO, Property.class);
        Address address = modelMapper.map(propertyAddDTO, Address.class);
        property.setAddress(address);
        PersonNameContact ownerDetails = modelMapper.map(propertyAddDTO, PersonNameContact.class);
        property.setOwnerDetails(ownerDetails);

        log.info("Attempt to save property: " + property);
        return propertyRepository.save(property);
    }

    public PropertyDTO findById(Long id) {
        Optional<Property> optionalProperty = propertyRepository.findById(id);
        if (optionalProperty.isEmpty())
            throw new ElementNotFoundException("Nie znalazłem mieszkania o podanym id.");

        Property propertyToShow = optionalProperty.get();
        PropertyDTO propertyData = modelMapper.map(propertyToShow, PropertyDTO.class);

        PersonNameContact ownerDetails = propertyToShow.getOwnerDetails();
        propertyData.setFirstName(ownerDetails.getFirstName());
        propertyData.setLastName(ownerDetails.getLastName());
        propertyData.setEmail(ownerDetails.getEmail());

        Address propertyAddress = propertyToShow.getAddress();

        propertyData.setCityName(propertyAddress.getCityName());
        propertyData.setStreetName(propertyAddress.getStreetName());
        propertyData.setStreetNumber(propertyAddress.getStreetNumber());
        propertyData.setApartmentNumber(propertyAddress.getApartmentNumber());

        return propertyData;
    }

}
