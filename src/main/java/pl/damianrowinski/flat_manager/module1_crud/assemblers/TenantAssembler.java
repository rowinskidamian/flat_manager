package pl.damianrowinski.flat_manager.module1_crud.assemblers;

import pl.damianrowinski.flat_manager.app_common.dtos.TenantTransferDTO;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.entities.Room;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.entities.Tenant;

public class TenantAssembler {
    public TenantTransferDTO convertToTransferData(Tenant tenant) {
        Double rentWithoutRoom = 0d;
        Room tenantRoom = tenant.getRoom();
        TenantTransferDTO tenantData = new TenantTransferDTO();

        if (tenantRoom != null) {
            Double catalogRent = tenantRoom.getCatalogRent();
            Double rentDiscount = tenant.getRentDiscount();

            if (rentDiscount != null) {
                tenantData.setRoomRent(catalogRent - rentDiscount);
            } else tenantData.setRoomRent(catalogRent);
        } else tenantData.setRoomRent(rentWithoutRoom);

        tenantData.setTenantId(tenant.getId());
        tenantData.setTenantName(tenant.getPersonalDetails().getFullName());

        return tenantData;
    }
}
