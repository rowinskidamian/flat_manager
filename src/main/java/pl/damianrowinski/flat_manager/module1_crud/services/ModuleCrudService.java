package pl.damianrowinski.flat_manager.module1_crud.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.damianrowinski.flat_manager.app_common.dtos.TenantTransferDTO;
import pl.damianrowinski.flat_manager.module2_analytics.services.ModuleAnalyticsService;

@Component
@RequiredArgsConstructor
@Slf4j
public class ModuleCrudService {

    private final ModuleAnalyticsService tenantReceiver;

    public void openPaymentBalanceFor(TenantTransferDTO tenantData) {
        log.info("Attempt to open PaymentBalance for Tenant with data: " + tenantData);
        tenantReceiver.openPaymentBalanceFor(tenantData);
    }

}
