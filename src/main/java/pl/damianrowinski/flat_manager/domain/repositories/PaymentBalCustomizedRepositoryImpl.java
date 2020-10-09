package pl.damianrowinski.flat_manager.domain.repositories;

import pl.damianrowinski.flat_manager.domain.model.entities.PaymentBalance;
import pl.damianrowinski.flat_manager.domain.model.entities.PaymentBalanceType;
import pl.damianrowinski.flat_manager.utils.LoggedUsername;

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
    public Optional<PaymentBalance> findLatestBalanceForTenant(Long tenantId) {
        Query q = entityManager
                .createQuery("SELECT pb FROM PaymentBalance pb WHERE pb.balanceHolderId = :tenantId AND " +
                        "pb.paymentHolderType = :holderType ORDER BY pb.createdDate DESC");
        q.setParameter("tenantId", tenantId);
        q.setParameter("holderType", PaymentBalanceType.TENANT);
        Optional<PaymentBalance> optionalPaymentBalance = getPaymentBalance(q);
        return optionalPaymentBalance;
    }

    @Override
    public Optional<PaymentBalance> findLatestBalanceForProperty(Long propertyId) {
        Query q = entityManager
                .createQuery("SELECT pb FROM PaymentBalance pb WHERE pb.balanceHolderId = :propertyId AND " +
                        "pb.paymentHolderType = :holderType ORDER BY pb.createdDate DESC");
        q.setParameter("propertyId", propertyId);
        q.setParameter("holderType", PaymentBalanceType.PROPERTY);
        return getPaymentBalance(q);
    }

    @Override
    public Optional<PaymentBalance> findLatestBalanceLoggedUser() {
        String loggedUserName = LoggedUsername.get();
        Query q = entityManager
                .createQuery("SELECT pb FROM PaymentBalance pb WHERE pb.loggedUserName = :loggedUserName AND " +
                        "pb.paymentHolderType = :holderType ORDER BY pb.createdDate DESC");
        q.setParameter("loggedUserName", loggedUserName);
        q.setParameter("holderType", PaymentBalanceType.USER);
        return getPaymentBalance(q);
    }

    private Optional<PaymentBalance> getPaymentBalance(Query q) {
        return (Optional<PaymentBalance>) q.getResultList()
                .stream()
                .findFirst();
    }

}
