package pl.damianrowinski.flat_manager.module1_crud.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.entities.PaymentBalance;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PaymentBalanceRepository extends JpaRepository<PaymentBalance, Long> {
}
