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
@Table(name = PaymentBalance.TABLE_NAME)
@SQLDelete(sql = PaymentBalance.SQL_UPDATE, check = ResultCheckStyle.COUNT)
@Where(clause = "state <> 'DELETED'")

public class PaymentBalance extends BaseEntityOwner {

    final static String TABLE_NAME = "payment_balance";
    final static String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET state = 'DELETED' WHERE id = ?";

    @Column(nullable = false, name = "current_balance_date")
    private LocalDateTime currentBalanceDate;

    @Column(nullable = false, name = "current_balance")
    private Double currentBalance;

    @Column(nullable = false, name = "balance_holder_id")
    private Long balanceHolderId;

    @Column(nullable = false, name = "balance_holder_name")
    private String balanceHolderName;

    @Column(name = "payment_holder_type")
    @Enumerated(EnumType.STRING)
    private PaymentBalanceType paymentHolderType;

}
