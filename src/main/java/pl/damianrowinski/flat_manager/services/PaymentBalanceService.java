package pl.damianrowinski.flat_manager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.domain.model.dtos.payment_balance.PaymentBalanceUpdateDTO;
import pl.damianrowinski.flat_manager.domain.model.dtos.payment_balance.TenantPayBalCreateDTO;
import pl.damianrowinski.flat_manager.assemblers.PaymentBalanceAssembler;
import pl.damianrowinski.flat_manager.domain.model.entities.PaymentBalance;
import pl.damianrowinski.flat_manager.domain.repositories.PaymentBalanceRepository;
import pl.damianrowinski.flat_manager.exceptions.ElementNotFoundException;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PaymentBalanceService {

    private final PaymentBalanceRepository paymentBalanceRepository;
    private final PaymentBalanceAssembler paymentBalanceAssembler;

    public void createPaymentBalanceForTenant(TenantPayBalCreateDTO tenantData) {
        PaymentBalance accountToSave = paymentBalanceAssembler.createTenantPaymentBalance(tenantData);
        paymentBalanceRepository.save(accountToSave);
        if (tenantData.getPropertyId() != null){
            PaymentBalanceUpdateDTO pbu = paymentBalanceAssembler.convertToPaymentBalanceUpdate(tenantData);
            checkAndUpdatePropertyPaymentBalanceFor(pbu);
        }
    }

    public void updateForPayment(PaymentBalanceUpdateDTO paymentData) {
        checkAndUpdateTenantBalance(paymentData);
        checkAndUpdatePropertyPaymentBalanceFor(paymentData);
    }

    private void checkAndUpdateTenantBalance(PaymentBalanceUpdateDTO paymentData) {
        Optional<PaymentBalance> optionalPaymentBalance =
                paymentBalanceRepository.findLatestBalanceForTenant(paymentData.getTenantId());

        if (optionalPaymentBalance.isEmpty()) throw new ElementNotFoundException("Dodaj najemcę, następnie płatność.");

        PaymentBalance paymentBalanceToUpdate = optionalPaymentBalance.get();
        PaymentBalance accountUpdated = paymentBalanceAssembler
                .updateTenantPaymentBalanceWithPayment(paymentBalanceToUpdate, paymentData.getPaymentAmount());
        paymentBalanceRepository.save(accountUpdated);
    }

    private void checkAndUpdatePropertyPaymentBalanceFor(PaymentBalanceUpdateDTO paymentBalanceChanges) {
        Optional<PaymentBalance> optionalPaymentBalance =
                paymentBalanceRepository.findLatestBalanceForProperty(paymentBalanceChanges.getPropertyId());

        if (optionalPaymentBalance.isPresent()) {
            PaymentBalance paymentBalanceCurrent = optionalPaymentBalance.get();
            PaymentBalance accountUpdated = paymentBalanceAssembler
                    .updatePropertyPaymentBalance(paymentBalanceChanges, paymentBalanceCurrent);
            paymentBalanceRepository.save(accountUpdated);
        } else {
            PaymentBalance accountToCreate = paymentBalanceAssembler
                    .createPropertyBalanceForTenant(paymentBalanceChanges);
            paymentBalanceRepository.save(accountToCreate);
        }
    }

}
