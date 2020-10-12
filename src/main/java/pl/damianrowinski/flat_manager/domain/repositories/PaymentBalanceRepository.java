package pl.damianrowinski.flat_manager.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.damianrowinski.flat_manager.domain.model.entities.PaymentBalance;
import pl.damianrowinski.flat_manager.domain.model.entities.PaymentBalanceType;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface PaymentBalanceRepository extends JpaRepository<PaymentBalance, Long>, PaymentBalCustomizedRepository {

        Optional<PaymentBalance> findFirstByPaymentHolderTypeOrderByCurrentBalanceDateDesc(PaymentBalanceType type);
}
