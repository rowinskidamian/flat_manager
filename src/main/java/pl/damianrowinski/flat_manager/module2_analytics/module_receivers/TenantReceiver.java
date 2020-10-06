package pl.damianrowinski.flat_manager.module2_analytics.module_receivers;

import lombok.RequiredArgsConstructor;
import pl.damianrowinski.flat_manager.app_common.dtos.TenantTransferDTO;
import pl.damianrowinski.flat_manager.app_common.dtos.TenantTransferType;
import pl.damianrowinski.flat_manager.module2_analytics.services.PaymentBalanceService;

@RequiredArgsConstructor
public class TenantReceiver {

    private final PaymentBalanceService paymentBalanceService;

    public void receive(TenantTransferDTO tenantData) {
        if (tenantData.getTransferType().equals(TenantTransferType.CREATE))
        paymentBalanceService.createPaymentBalanceFor(tenantData);
    }


}
