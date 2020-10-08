package pl.damianrowinski.flat_manager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.domain.model.dtos.PaymentTransferDTO;
import pl.damianrowinski.flat_manager.domain.model.dtos.TenantTransferDTO;
import pl.damianrowinski.flat_manager.assemblers.PaymentBalanceAssembler;
import pl.damianrowinski.flat_manager.domain.model.entities.PaymentBalance;
import pl.damianrowinski.flat_manager.domain.repositories.PaymentBalanceRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PaymentBalanceService {

    private final PaymentBalanceRepository paymentBalanceRepository;
    private final PaymentBalanceAssembler paymentBalanceAssembler;

    public void updatePaymentBalanceForTenant(TenantTransferDTO tenantData) {
        PaymentBalance accountToSave = paymentBalanceAssembler.createTenantPaymentBalance(tenantData);
        paymentBalanceRepository.save(accountToSave);
        if (tenantData.getPropertyId() != null)
            checkAndUpdatePropertyPaymentBalanceFor(tenantData);
    }

    private void checkAndUpdatePropertyPaymentBalanceFor(TenantTransferDTO tenantData) {
        Optional<PaymentBalance> optionalPaymentBalance =
                paymentBalanceRepository.findLatestBalanceForProperty(tenantData.getPropertyId());

        if (optionalPaymentBalance.isPresent()) {
            PaymentBalance paymentBalanceToUpdate = optionalPaymentBalance.get();
            PaymentBalance accountUpdated = paymentBalanceAssembler
                    .updatePropertyPaymentBalanceWithTenant(tenantData, paymentBalanceToUpdate);
            paymentBalanceRepository.save(accountUpdated);
        } else {
            PaymentBalance accountToCreate = paymentBalanceAssembler.createPropertyBalanceFromTenant(tenantData);
            paymentBalanceRepository.save(accountToCreate);
        }
    }


    public void updatePaymentBalanceForTenantPayment(PaymentTransferDTO tenantPaymentData) {
        PaymentBalance tenantPaymentBalanceUpdate = paymentBalanceAssembler.createPaymentBalanceFor(tenantPaymentData);
        checkAndUpdateTenantBalance(tenantPaymentBalanceUpdate);
        checkAndUpdatePropertyPaymentBalanceFor(tenantPaymentBalanceUpdate);
    }

    private void checkAndUpdateTenantBalance(PaymentBalance tenantBalance) {
        Optional<PaymentBalance> optionalPaymentBalance =
                paymentBalanceRepository.findLatestBalanceForTenant(tenantBalance.getBalanceHolderId());

        if (optionalPaymentBalance.isPresent()) {
            PaymentBalance paymentBalanceToUpdate = optionalPaymentBalance.get();
            PaymentBalance accountUpdated = paymentBalanceAssembler
                    .updateTenantPaymentBalanceWithPayment(paymentBalanceToUpdate, tenantBalance.getCurrentBalance());
            paymentBalanceRepository.save(accountUpdated);
        }
    }

    private void checkAndUpdatePropertyPaymentBalanceFor(PaymentBalance balanceToUpdate) {
    }
}
