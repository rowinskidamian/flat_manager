package pl.damianrowinski.flat_manager.module1_crud.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.entities.Payment;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllByLoggedUserNameOrderByPaymentDateDesc(String loggedUserName);

    List<Payment> findAllByTenantIdOrderByPaymentDateDesc(Long tenantId);

}
