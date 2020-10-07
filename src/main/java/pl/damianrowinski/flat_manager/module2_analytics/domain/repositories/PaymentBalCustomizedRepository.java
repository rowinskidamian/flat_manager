package pl.damianrowinski.flat_manager.module2_analytics.domain.repositories;

import pl.damianrowinski.flat_manager.module2_analytics.domain.model.entities.PaymentBalance;

import java.util.Optional;

public interface PaymentBalCustomizedRepository {

    Optional<PaymentBalance> findLatestBalanceForProperty(Long propertyId);
}
