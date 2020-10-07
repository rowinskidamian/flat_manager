package pl.damianrowinski.flat_manager.module2_analytics.assemblers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.damianrowinski.flat_manager.app_common.dtos.PaymentTransferDTO;
import pl.damianrowinski.flat_manager.app_common.dtos.TenantTransferDTO;
import pl.damianrowinski.flat_manager.module2_analytics.domain.model.entities.PaymentBalance;
import pl.damianrowinski.flat_manager.module2_analytics.domain.model.entities.PaymentBalanceType;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class PaymentBalanceAssembler {

    public PaymentBalance createTenantPaymentBalance(TenantTransferDTO tenantData) {
        PaymentBalance accountToCreate = new PaymentBalance();
        accountToCreate.setCurrentBalanceDate(LocalDateTime.now());
        accountToCreate.setBalanceHolderId(tenantData.getTenantId());
        accountToCreate.setBalanceHolderName(tenantData.getTenantName());
        accountToCreate.setCurrentBalance(-tenantData.getRoomRent());
        accountToCreate.setPaymentHolderType(PaymentBalanceType.TENANT);
        return accountToCreate;
    }

    public PaymentBalance updatePropertyPaymentBalanceWithTenant
            (TenantTransferDTO tenantData, PaymentBalance paymentBalance) {
        PaymentBalance updatedPaymentBalance = new PaymentBalance();
        updatedPaymentBalance.setCurrentBalanceDate(LocalDateTime.now());
        updatedPaymentBalance.setBalanceHolderId(paymentBalance.getBalanceHolderId());
        updatedPaymentBalance.setBalanceHolderName(paymentBalance.getBalanceHolderName());

        Double paymentBalanceAmount = paymentBalance.getCurrentBalance();
        Double roomRent = tenantData.getRoomRent();
        Double paymentBalanceAmountUpdated = paymentBalanceAmount - roomRent;
        updatedPaymentBalance.setCurrentBalance(paymentBalanceAmountUpdated);
        updatedPaymentBalance.setPaymentHolderType(PaymentBalanceType.PROPERTY);

        return updatedPaymentBalance;
    }

    public PaymentBalance createPropertyBalanceFromTenant(TenantTransferDTO tenantData) {
        PaymentBalance accountToCreate = new PaymentBalance();
        accountToCreate.setCurrentBalanceDate(LocalDateTime.now());
        accountToCreate.setBalanceHolderId(tenantData.getPropertyId());
        accountToCreate.setBalanceHolderName(tenantData.getPropertyName());
        accountToCreate.setCurrentBalance(-tenantData.getRoomRent());
        accountToCreate.setPaymentHolderType(PaymentBalanceType.PROPERTY);
        return accountToCreate;
    }

    public PaymentBalance createPaymentBalanceFor(PaymentTransferDTO tenantPaymentData) {
        return null;
    }

    public PaymentBalance updateTenantPaymentBalanceWithPayment(PaymentBalance paymentBalanceToUpdate,
                                                                PaymentBalance tenantBalance) {
        PaymentBalance tenantBalanceUpdated = new PaymentBalance();
        tenantBalanceUpdated.setCurrentBalanceDate(LocalDateTime.now());
        tenantBalanceUpdated.setBalanceHolderId(paymentBalanceToUpdate.getBalanceHolderId());
        tenantBalanceUpdated.setBalanceHolderName(paymentBalanceToUpdate.getBalanceHolderName());
        tenantBalanceUpdated.setPaymentHolderType(PaymentBalanceType.PROPERTY);

        Double balanceAmountUpdated = paymentBalanceToUpdate.getCurrentBalance() + tenantBalance.getCurrentBalance();
        tenantBalanceUpdated.setCurrentBalance(balanceAmountUpdated);
        return tenantBalanceUpdated;
    }
}
