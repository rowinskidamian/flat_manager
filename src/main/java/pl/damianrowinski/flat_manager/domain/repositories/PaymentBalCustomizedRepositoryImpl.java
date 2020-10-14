package pl.damianrowinski.flat_manager.domain.repositories;

import lombok.extern.slf4j.Slf4j;
import pl.damianrowinski.flat_manager.domain.model.entities.PaymentBalance;
import pl.damianrowinski.flat_manager.domain.model.entities.PaymentBalanceType;
import pl.damianrowinski.flat_manager.utils.LoggedUsername;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.*;

@Transactional
@Slf4j
public class PaymentBalCustomizedRepositoryImpl implements PaymentBalCustomizedRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<PaymentBalance> findLatestBalanceForTenant(Long tenantId) {
        Query q = entityManager
                .createQuery("SELECT pb FROM PaymentBalance pb WHERE pb.balanceHolderId = :tenantId AND " +
                        "pb.paymentHolderType = :holderType AND pb.loggedUserName = :loggedUserName ORDER BY pb.id DESC");
        q.setParameter("tenantId", tenantId);
        q.setParameter("holderType", PaymentBalanceType.TENANT);
        q.setParameter("loggedUserName", LoggedUsername.get());
        return getPaymentBalance(q);
    }

    @Override
    public Optional<PaymentBalance> findLatestBalanceForProperty(Long propertyId) {
        Query q = entityManager
                .createQuery("SELECT pb FROM PaymentBalance pb WHERE pb.balanceHolderId = :propertyId AND " +
                        "pb.paymentHolderType = :holderType AND pb.loggedUserName = :loggedUserName ORDER BY pb.id DESC");
        q.setParameter("propertyId", propertyId);
        q.setParameter("holderType", PaymentBalanceType.PROPERTY);
        q.setParameter("loggedUserName", LoggedUsername.get());
        return getPaymentBalance(q);
    }

    @Override
    public Optional<PaymentBalance> findLatestBalanceLoggedUser() {
        String loggedUserName = LoggedUsername.get();
        Query q = entityManager
                .createQuery("SELECT pb FROM PaymentBalance pb WHERE pb.loggedUserName = :loggedUserName AND " +
                        "pb.paymentHolderType = :holderType ORDER BY pb.id DESC");
        q.setParameter("loggedUserName", loggedUserName);
        q.setParameter("holderType", PaymentBalanceType.USER);
        return getPaymentBalance(q);
    }

    @Override
    public List<PaymentBalance> getListLatestBalanceFor(PaymentBalanceType type) {
        String sqlQuery = "SELECT pb FROM PaymentBalance pb WHERE pb.paymentHolderType = :balanceType " +
                " AND pb.loggedUserName = :loggedUserName ORDER BY pb.id DESC";
        TypedQuery<PaymentBalance> query = entityManager.createQuery(sqlQuery, PaymentBalance.class);
        query.setParameter("balanceType", type);
        query.setParameter("loggedUserName", LoggedUsername.get());
        List<PaymentBalance> listToFilter = query.getResultList();
        return getUniqueValuesList(listToFilter);
    }

    private List<PaymentBalance> getUniqueValuesList(List<PaymentBalance> listToFilter) {
        Map<Long, PaymentBalance> filteredMap = new HashMap<>();

        for (PaymentBalance currentPB : listToFilter) {
            Long balanceHolderId = currentPB.getBalanceHolderId();
            filteredMap.putIfAbsent(balanceHolderId, currentPB);
        }
        return new ArrayList<>(filteredMap.values());
    }

    private Optional<PaymentBalance> getPaymentBalance(Query q) {
        return (Optional<PaymentBalance>) q.getResultList()
                .stream()
                .findFirst();
    }

}
