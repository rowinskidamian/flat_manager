package pl.damianrowinski.flat_manager.module2_analytics.domain.repositories;

import pl.damianrowinski.flat_manager.module2_analytics.domain.model.entities.PaymentBalance;
import pl.damianrowinski.flat_manager.module2_analytics.domain.model.entities.PaymentBalanceType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public class PaymentBalCustomizedRepositoryImpl implements PaymentBalCustomizedRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<PaymentBalance> findLatestBalanceForProperty(Long propertyId) {
        Query q = entityManager
                .createQuery("SELECT pb FROM PaymentBalance pb WHERE pb.balanceHolderId = :propertyId AND " +
                        "pb.paymentHolderType = :holderType ORDER BY pb.createdDate DESC");
        q.setParameter("propertyId", propertyId);
        q.setParameter("holderType", PaymentBalanceType.PROPERTY);
        Optional<PaymentBalance> optionalPaymentBalance = q.getResultList()
                .stream()
                .findFirst();
        return optionalPaymentBalance;
    }

    @Override
    public Optional<PaymentBalance> findLatestBalanceForTenant(Long tenantId) {
        Query q = entityManager
                .createQuery("SELECT pb FROM PaymentBalance pb WHERE pb.balanceHolderId = :tenantId AND " +
                        "pb.paymentHolderType = :holderType ORDER BY pb.createdDate DESC");
        q.setParameter("tenantId", tenantId);
        q.setParameter("holderType", PaymentBalanceType.TENANT);
        Optional<PaymentBalance> optionalPaymentBalance = q.getResultList()
                .stream()
                .findFirst();
        return optionalPaymentBalance;
    }

}
