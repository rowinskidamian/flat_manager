package pl.damianrowinski.flat_manager.domain.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Transactional
@Table(name = AccountBalance.TABLE_NAME)
public class AccountBalance extends BaseEntityLoggedUser {

    final static String TABLE_NAME = "account_balance";

    @Column(nullable = false, name = "current_billing_period")
    private LocalDateTime currentBillingPeriod;

    @Column(nullable = false, name = "current_balance")
    private Double currentBalance;

}
