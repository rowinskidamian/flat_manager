package pl.damianrowinski.flat_manager.module2_analytics.domain.model.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.entities.BaseEntityOwner;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Transactional
@Table(name = BusinessBalance.TABLE_NAME)
@SQLDelete(sql = BusinessBalance.SQL_UPDATE, check = ResultCheckStyle.COUNT)
@Where(clause = "state <> 'DELETED'")

public class BusinessBalance extends BaseEntityOwner {

    final static String TABLE_NAME = "business_balance";
    final static String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET state = 'DELETED' WHERE id = ?";

    @Column(nullable = false, name = "current_balance_date")
    private LocalDateTime currentBalanceDate;

    @Column(nullable = false, name = "incomes")
    private Double incomes;
    @Column(nullable = false, name = "expenses")
    private Double expenses;
    @Column(nullable = false, name = "current_balance")
    private Double currentBalance;

    @Column(nullable = false, name = "balance_holder_id")
    private Long balanceHolderId;

    @Column(name = "business_holder_type")
    @Enumerated(EnumType.STRING)
    private BusinessBalanceType businessHolderType;



}
