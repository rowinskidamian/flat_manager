package pl.damianrowinski.flat_manager.module2_analytics.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.app_common.dtos.TenantTransferDTO;
import pl.damianrowinski.flat_manager.module2_analytics.assemblers.PaymentBalanceAssembler;
import pl.damianrowinski.flat_manager.module2_analytics.domain.model.entities.PaymentBalance;
import pl.damianrowinski.flat_manager.module2_analytics.domain.repositories.PaymentBalanceRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PaymentBalanceService {

    private final PaymentBalanceRepository paymentBalanceRepository;
    private final PaymentBalanceAssembler paymentBalanceAssembler;

    public void createPaymentBalanceFor(TenantTransferDTO tenantData) {
        PaymentBalance accountToSave = paymentBalanceAssembler.openAccountForTenant(tenantData);
        paymentBalanceRepository.save(accountToSave);
    }


}
