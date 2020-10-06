package pl.damianrowinski.flat_manager.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.damianrowinski.flat_manager.domain.model.entities.PaymentBalance;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface AccountBalanceRepository extends JpaRepository<PaymentBalance, Long> {
}
