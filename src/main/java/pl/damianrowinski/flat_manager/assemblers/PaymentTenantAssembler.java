package pl.damianrowinski.flat_manager.assemblers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.damianrowinski.flat_manager.domain.model.dtos.payment_balance.TenantPayBalCreateDTO;
import pl.damianrowinski.flat_manager.domain.model.dtos.property.PropertyEditDTO;
import pl.damianrowinski.flat_manager.domain.model.dtos.room.RoomEditDTO;
import pl.damianrowinski.flat_manager.domain.model.dtos.tenant.TenantEditDTO;
import pl.damianrowinski.flat_manager.services.PropertyService;
import pl.damianrowinski.flat_manager.services.RoomService;

@Component
@RequiredArgsConstructor
public class PaymentTenantAssembler {

    private final RoomService roomService;
    private final PropertyService propertyService;

    public TenantPayBalCreateDTO getTenantPaymentBalanceData(TenantEditDTO tenant) {
        Double rentWithoutRoom = 0d;
        Long tenantRoomId = tenant.getRoomId();
        RoomEditDTO roomData = roomService.findRoomToEdit(tenantRoomId);

        TenantPayBalCreateDTO tenantData = new TenantPayBalCreateDTO();

        if (roomData != null) {
            Double catalogRent = roomData.getCatalogRent();
            Double rentDiscount = tenant.getRentDiscount();
            tenantData.setPropertyId(roomData.getPropertyId());
            PropertyEditDTO propertyData = propertyService.findToEditById(roomData.getPropertyId());
            tenantData.setPropertyName(propertyData.getWorkingName());

            if (rentDiscount != null) {
                tenantData.setRoomRent(catalogRent - rentDiscount);
            } else tenantData.setRoomRent(catalogRent);
        } else tenantData.setRoomRent(rentWithoutRoom);

        tenantData.setTenantId(tenant.getId());
        tenantData.setTenantName(tenant.getFullName());

        return tenantData;
    }
}
