package pl.damianrowinski.flat_manager.domain.repositories;

import pl.damianrowinski.flat_manager.domain.model.entities.PaymentBalance;
import pl.damianrowinski.flat_manager.domain.model.entities.PaymentBalanceType;

import java.util.List;
import java.util.Optional;

public interface PaymentBalCustomizedRepository {

    Optional<PaymentBalance> findLatestBalanceForProperty(Long propertyId);

    Optional<PaymentBalance> findLatestBalanceForTenant(Long tenantId);

    Optional<PaymentBalance> findLatestBalanceLoggedUser();

    List<PaymentBalance> getListLatestBalanceFor(PaymentBalanceType type);
}
