package pl.damianrowinski.flat_manager.module1_crud.assemblers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.damianrowinski.flat_manager.app_common.dtos.TenantTransferDTO;
import pl.damianrowinski.flat_manager.app_common.dtos.TenantTransferType;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.dtos.room.RoomEditDTO;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.dtos.tenant.TenantEditDTO;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.entities.Room;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.entities.Tenant;
import pl.damianrowinski.flat_manager.module1_crud.domain.repositories.RoomRepository;
import pl.damianrowinski.flat_manager.module1_crud.services.RoomService;

@Component
@RequiredArgsConstructor
public class TenantAssembler {

    private final RoomService roomService;

    public TenantTransferDTO convertToTransferCreateData(TenantEditDTO tenant) {
        Double rentWithoutRoom = 0d;
        Long tenantRoomId = tenant.getRoomId();
        RoomEditDTO roomData = roomService.findRoomToEdit(tenantRoomId);

        TenantTransferDTO tenantData = new TenantTransferDTO();

        if (roomData != null) {
            Double catalogRent = roomData.getCatalogRent();
            Double rentDiscount = tenant.getRentDiscount();

            if (rentDiscount != null) {
                tenantData.setRoomRent(catalogRent - rentDiscount);
            } else tenantData.setRoomRent(catalogRent);
        } else tenantData.setRoomRent(rentWithoutRoom);

        tenantData.setTenantId(tenant.getId());
        tenantData.setTenantName(tenant.getFullName());
        tenantData.setTransferType(TenantTransferType.CREATE);

        return tenantData;
    }
}
