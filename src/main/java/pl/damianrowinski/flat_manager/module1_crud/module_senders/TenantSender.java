package pl.damianrowinski.flat_manager.module1_crud.module_senders;

import lombok.RequiredArgsConstructor;
import pl.damianrowinski.flat_manager.app_common.dtos.TenantTransferDTO;
import pl.damianrowinski.flat_manager.module2_analytics.module_receivers.TenantReceiver;

@RequiredArgsConstructor
public class TenantSender {

    private final TenantReceiver tenantReceiver;

    public void send(TenantTransferDTO tenantData) {
        tenantReceiver.receive(tenantData);
    }

}
