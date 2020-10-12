package pl.damianrowinski.flat_manager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.domain.model.dtos.payment_balance.PaymentBalanceUpdateDTO;
import pl.damianrowinski.flat_manager.domain.model.dtos.payment_balance.TenantPayBalCreateDTO;
import pl.damianrowinski.flat_manager.assemblers.PaymentBalanceAssembler;
import pl.damianrowinski.flat_manager.domain.model.dtos.tenant.TenantListDTO;
import pl.damianrowinski.flat_manager.domain.model.dtos.tenant.TenantShowDTO;
import pl.damianrowinski.flat_manager.domain.model.entities.PaymentBalance;
import pl.damianrowinski.flat_manager.domain.repositories.PaymentBalanceRepository;
import pl.damianrowinski.flat_manager.exceptions.ElementNotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PaymentBalanceService {

    private final PaymentBalanceRepository paymentBalanceRepository;
    private final PaymentBalanceAssembler paymentBalanceAssembler;

    public void createPaymentBalanceForTenant(TenantPayBalCreateDTO tenantData) {

        Optional<PaymentBalance> optionalPaymentBalance =
                paymentBalanceRepository.findLatestBalanceForTenant(tenantData.getTenantId());

        if (optionalPaymentBalance.isEmpty()) {
            PaymentBalance accountToSave = paymentBalanceAssembler.createTenantPaymentBalance(tenantData);
            paymentBalanceRepository.save(accountToSave);
        } else {
            PaymentBalance paymentBalance = optionalPaymentBalance.get();
            PaymentBalance accountUpdated = paymentBalanceAssembler
                    .updateTenantPaymentBalanceByCheckin(paymentBalance, tenantData.getRoomRent());
            paymentBalanceRepository.save(accountUpdated);
        }

        PaymentBalanceUpdateDTO pbu = paymentBalanceAssembler.convertToPaymentBalanceUpdate(tenantData);
        checkAndUpdatePropertyPaymentBalanceFor(pbu);
        checkAndUpdateUserPaymentBalance(pbu);
    }

    public void updateForPayment(PaymentBalanceUpdateDTO paymentData) {
        checkAndUpdateTenantBalance(paymentData);
        checkAndUpdatePropertyPaymentBalanceFor(paymentData);
        checkAndUpdateUserPaymentBalance(paymentData);
    }

    public void collectRentFromTenants(List<TenantShowDTO> tenantList) {
        for (TenantShowDTO tenantData : tenantList) {
            Long roomId = tenantData.getRoomId();
            if (roomId != null) {

                PaymentBalanceUpdateDTO accountToUpdate = paymentBalanceAssembler
                        .convertToAccountUpdateWithRentRequest(tenantData);
                log.info("Updated account balance: " + accountToUpdate);
                updateForPayment(accountToUpdate);
            }
        }
    }

    private void checkAndUpdateTenantBalance(PaymentBalanceUpdateDTO paymentData) {
        PaymentBalance paymentBalanceToUpdate = getTenantPaymentBalance(paymentData);
        PaymentBalance accountUpdated = paymentBalanceAssembler
                .updateTenantPaymentBalanceWithPayment(paymentBalanceToUpdate, paymentData);
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

    private void checkAndUpdateUserPaymentBalance(PaymentBalanceUpdateDTO paymentBalanceChanges) {
        Optional<PaymentBalance> optionalBalance = paymentBalanceRepository.findLatestBalanceLoggedUser();

        if(optionalBalance.isPresent()) {
            PaymentBalance currentBalance = optionalBalance.get();;
            PaymentBalance accountUpdated = paymentBalanceAssembler
                    .updateUserPaymentBalance(paymentBalanceChanges, currentBalance);
            paymentBalanceRepository.save(accountUpdated);
        } else {
            PaymentBalance accountToCreate = paymentBalanceAssembler
                    .createUserBalance(paymentBalanceChanges);
            paymentBalanceRepository.save(accountToCreate);
        }
    }

    private PaymentBalance getTenantPaymentBalance(PaymentBalanceUpdateDTO paymentData) {
        Optional<PaymentBalance> optionalPaymentBalance =
                paymentBalanceRepository.findLatestBalanceForTenant(paymentData.getTenantId());

        if (optionalPaymentBalance.isEmpty())
            throw new ElementNotFoundException("Brak założonego konta płatności dla najemcy.");

        return optionalPaymentBalance.get();
    }

}
