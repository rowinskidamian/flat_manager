package pl.damianrowinski.flat_manager.domain.entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Entity
@Transactional
public class AccountBalance extends BaseEntity {

    @OneToOne
    private Tenant tenant;

    private LocalDateTime currentBillingPeriod;



}
