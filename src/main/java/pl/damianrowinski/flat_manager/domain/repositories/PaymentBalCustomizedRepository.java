package pl.damianrowinski.flat_manager.domain.repositories;

import pl.damianrowinski.flat_manager.domain.model.entities.PaymentBalance;

import java.util.Optional;

public interface PaymentBalCustomizedRepository {

    Optional<PaymentBalance> findLatestBalanceForProperty(Long propertyId);

    Optional<PaymentBalance> findLatestBalanceForTenant(Long tenantId);

    Optional<PaymentBalance> findLatestBalanceLoggedUser();
}
