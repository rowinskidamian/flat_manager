package pl.damianrowinski.flat_manager.module2_analytics.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.damianrowinski.flat_manager.app_common.dtos.TenantTransferDTO;
import pl.damianrowinski.flat_manager.app_common.dtos.TenantTransferType;

@Component
@RequiredArgsConstructor
public class ModuleAnalyticsService {

    private final PaymentBalanceService paymentBalanceService;

    public void sendTenantForCreateBalance(TenantTransferDTO tenantData) {
        if (tenantData.getTransferType().equals(TenantTransferType.CREATE))
        paymentBalanceService.receiveTenantOpenPaymentBalance(tenantData);
    }


}
