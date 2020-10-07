package pl.damianrowinski.flat_manager.module2_analytics.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.app_common.dtos.TenantTransferDTO;
import pl.damianrowinski.flat_manager.module2_analytics.assemblers.PaymentBalanceAssembler;
import pl.damianrowinski.flat_manager.module2_analytics.domain.model.entities.PaymentBalance;
import pl.damianrowinski.flat_manager.module2_analytics.domain.repositories.PaymentBalanceRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PaymentBalanceService {

    private final PaymentBalanceRepository paymentBalanceRepository;
    private final PaymentBalanceAssembler paymentBalanceAssembler;

    public void receiveTenantOpenPaymentBalance(TenantTransferDTO tenantData) {
        PaymentBalance accountToSave = paymentBalanceAssembler.createTenantPaymentBalance(tenantData);
        paymentBalanceRepository.save(accountToSave);
        if (tenantData.getPropertyId() != null)
            checkAndUpdatePropertyPaymentBalanceFor(tenantData);
    }

    private void checkAndUpdatePropertyPaymentBalanceFor(TenantTransferDTO tenantData) {
        Optional<PaymentBalance> optionalPaymentBalance =
                paymentBalanceRepository.findLatestBalanceForProperty(tenantData.getPropertyId());

        if (optionalPaymentBalance.isPresent()) {
            PaymentBalance paymentBalance = optionalPaymentBalance.get();
            PaymentBalance accountUpdated = paymentBalanceAssembler
                    .updatePropertyPaymentBalanceWithTenant(tenantData, paymentBalance);
            paymentBalanceRepository.save(accountUpdated);
        } else {
            PaymentBalance accountToCreate = paymentBalanceAssembler.createPropertyBalanceWithTenant(tenantData);
            paymentBalanceRepository.save(accountToCreate);
        }
    }


}
