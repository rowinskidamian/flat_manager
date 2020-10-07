package pl.damianrowinski.flat_manager.module2_analytics.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.damianrowinski.flat_manager.app_common.dtos.PaymentTransferDTO;
import pl.damianrowinski.flat_manager.app_common.dtos.TenantTransferDTO;
import pl.damianrowinski.flat_manager.app_common.dtos.TenantTransferType;

@Component
@RequiredArgsConstructor
public class ModuleAnalyticsService {

    private final PaymentBalanceService paymentBalanceService;

    public void getTenantForPaymentBalance(TenantTransferDTO tenantData) {
        if (tenantData.getTransferType().equals(TenantTransferType.CREATE))
        paymentBalanceService.updatePaymentBalanceForTenant(tenantData);
    }

    public void getPaymentForBalance(PaymentTransferDTO paymentData) {
        paymentBalanceService.updatePaymentBalanceForTenantPayment(paymentData);
    }


}
