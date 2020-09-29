package pl.damianrowinski.flat_manager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.domain.entities.Property;
import pl.damianrowinski.flat_manager.domain.entities.Room;
import pl.damianrowinski.flat_manager.domain.entities.Tenant;
import pl.damianrowinski.flat_manager.exceptions.ElementNotFoundException;
import pl.damianrowinski.flat_manager.exceptions.ObjectInRelationshipException;
import pl.damianrowinski.flat_manager.model.common.Address;
import pl.damianrowinski.flat_manager.model.common.PersonNameContact;
import pl.damianrowinski.flat_manager.model.dtos.TenantEditDTO;
import pl.damianrowinski.flat_manager.model.dtos.TenantListDTO;
import pl.damianrowinski.flat_manager.model.dtos.TenantShowDTO;
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
public class TenantService {

    private final TenantRepository tenantRepository;
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    public void delete(Long tenantId) {
        Optional<Tenant> optionalTenant = tenantRepository.findById(tenantId);
        if (optionalTenant.isEmpty()) throw new ElementNotFoundException("Nie znalazłem najemcy o podanym id");
        if (optionalTenant.get().getRoom() != null)
            throw new ObjectInRelationshipException("Pokój ma najemcę, najpierw usuń najemcę, a później pokój");
        tenantRepository.delete(optionalTenant.get());
    }

    public List<TenantListDTO> findAllWithoutRooms(String loggedUserName) {
        List<Tenant> tenantList = tenantRepository.findAllByLoggedUserNameAndRoomIsNull(loggedUserName);
        List<TenantListDTO> tenantDataList = new ArrayList<>();
        for (Tenant tenant : tenantList) {
            log.info("Tenant without room:");
            log.info(tenant.toString());

            TenantListDTO tenantData = new TenantListDTO();
            tenantData.setTenantId(tenant.getId());
            tenantData.setTenantFullName(tenant.getPersonalDetails().getFullName());
            tenantData.setLoggedUserName(tenant.getLoggedUserName());
            tenantDataList.add(tenantData);
        }
        return tenantDataList;
    }

    public List<TenantShowDTO> findAllLoggedUser(String loggedUserName) {
        List<Tenant> tenantList = tenantRepository.findAllByLoggedUserName(loggedUserName);
        List<TenantShowDTO> tenantDataList = new ArrayList<>();

        for (Tenant tenant : tenantList) {
            TenantShowDTO tenantData = modelMapper.map(tenant, TenantShowDTO.class);
            tenantData.setFullName(tenant.getPersonalDetails().getFullName());

            Room tenantRoom = tenant.getRoom();
            if (tenantRoom != null) {
                Property property = tenantRoom.getProperty();
                tenantData.setApartmentName(property.getWorkingName());
                Double currentRent = calculateCurrentRent(tenantData, tenantRoom);
                tenantData.setCurrentRent(currentRent);
                tenantData.setPropertyId(property.getId());
            }

            tenantData.setEmail(tenant.getPersonalDetails().getEmail());
            tenantDataList.add(tenantData);
        }

        return tenantDataList;
    }

    private Double calculateCurrentRent(TenantShowDTO tenantData, Room tenantRoom) {
        Double catalogRent = tenantRoom.getCatalogRent();
        Double rentDiscount = tenantData.getRentDiscount();
        return rentDiscount != null ? catalogRent - rentDiscount : catalogRent;
    }

    public void save(TenantEditDTO tenantDataToAdd) {
        Tenant tenantToAdd = modelMapper.map(tenantDataToAdd, Tenant.class);
        Address address = modelMapper.map(tenantDataToAdd, Address.class);
        tenantToAdd.setContactAddress(address);
        PersonNameContact personalDetails = modelMapper.map(tenantDataToAdd, PersonNameContact.class);
        tenantToAdd.setPersonalDetails(personalDetails);

        log.info("Attempt to save tenant: " + tenantToAdd);
        Tenant savedTenant = tenantRepository.save(tenantToAdd);

        Long tenantRoomId = tenantDataToAdd.getRoomId();
        if (tenantRoomId != null) {
            Optional<Room> optionalRoom = roomRepository.findById(tenantRoomId);
            if (optionalRoom.isEmpty()) throw new ElementNotFoundException("Nie znalazłem pokoju o podanym id.");
            Room room = optionalRoom.get();
            room.setTenant(savedTenant);
            roomRepository.save(room);
        }

    }
}
