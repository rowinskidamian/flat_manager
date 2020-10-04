package pl.damianrowinski.flat_manager.domain.model.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Transactional
@Table(name = AccountBalance.TABLE_NAME)
@SQLDelete(sql = AccountBalance.SQL_UPDATE, check = ResultCheckStyle.COUNT)
@Where(clause = "state <> 'DELETED'")

public class AccountBalance extends BaseEntityLoggedUser {

    final static String TABLE_NAME = "account_balance";
    final static String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET state = 'DELETED' WHERE id = ?";

    @Column(nullable = false, name = "current_billing_period")
    private LocalDateTime currentBillingPeriod;

    @Column(nullable = false, name = "current_balance")
    private Double currentBalance;

}
