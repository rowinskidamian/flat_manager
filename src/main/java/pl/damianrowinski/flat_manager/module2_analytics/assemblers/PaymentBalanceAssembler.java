package pl.damianrowinski.flat_manager.module2_analytics.assemblers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.damianrowinski.flat_manager.app_common.dtos.TenantTransferDTO;
import pl.damianrowinski.flat_manager.module2_analytics.domain.model.entities.PaymentBalance;
import pl.damianrowinski.flat_manager.module2_analytics.domain.model.entities.PaymentBalanceType;

@RequiredArgsConstructor
@Component
public class PaymentBalanceAssembler {

    public PaymentBalance openAccountForTenant(TenantTransferDTO tenantData) {
        PaymentBalance accountToCreate = new PaymentBalance();
        accountToCreate.setBalanceHolderId(tenantData.getTenantId());
        accountToCreate.setBalanceHolderName(tenantData.getTenantName());
        accountToCreate.setCurrentBalance(-tenantData.getRoomRent());
        accountToCreate.setPaymentHolderType(PaymentBalanceType.TENANT);
        return accountToCreate;
    }

}
