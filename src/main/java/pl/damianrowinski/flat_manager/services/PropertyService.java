package pl.damianrowinski.flat_manager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.domain.entities.Property;
import pl.damianrowinski.flat_manager.model.dtos.PropertyAddDTO;
import pl.damianrowinski.flat_manager.model.repositories.PropertyRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j

public class PropertyService {

    private final PropertyRepository propertyRepository;

    public void save(PropertyAddDTO propertyAddDTO) {
        Property property = new Property();
        property.setBillsRentAmount(propertyAddDTO.getBillsRentAmount());
        property.setBillsPaymentDate(propertyAddDTO.getBillsPaymentDate());
        property.setRooms(propertyAddDTO.getRooms());
        log.debug("Attempt to save property: " + property);
        propertyRepository.save(property);
    }

}
