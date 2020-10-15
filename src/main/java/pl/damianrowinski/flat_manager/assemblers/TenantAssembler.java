package pl.damianrowinski.flat_manager.assemblers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.damianrowinski.flat_manager.domain.model.common.Address;
import pl.damianrowinski.flat_manager.domain.model.common.PersonNameContact;
import pl.damianrowinski.flat_manager.domain.model.dtos.tenant.TenantEditDTO;
import pl.damianrowinski.flat_manager.domain.model.entities.Tenant;

@Component
@RequiredArgsConstructor

public class TenantAssembler {

    private final ModelMapper modelMapper;

    public TenantEditDTO convertTenantToEditWithRoom(Tenant savedTenant, Long roomId) {

        TenantEditDTO tenantData = modelMapper.map(savedTenant, TenantEditDTO.class);
        tenantData.setRoomId(roomId);
        PersonNameContact personalDetails = savedTenant.getPersonalDetails();
        Address contactAddress = savedTenant.getContactAddress();

        tenantData.setFirstName(personalDetails.getFirstName());
        tenantData.setLastName(personalDetails.getLastName());
        tenantData.setFullName(personalDetails.getFullName());
        tenantData.setEmail(personalDetails.getEmail());

        tenantData.setCityName(contactAddress.getCityName());
        tenantData.setStreetName(contactAddress.getStreetName());
        tenantData.setStreetNumber(contactAddress.getStreetNumber());
        if(contactAddress.getApartmentNumber() != null)
            tenantData.setApartmentNumber(contactAddress.getApartmentNumber());

        return tenantData;
    }
}
