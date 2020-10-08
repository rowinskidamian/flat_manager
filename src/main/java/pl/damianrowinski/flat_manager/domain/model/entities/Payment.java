package pl.damianrowinski.flat_manager.domain.model.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pl.damianrowinski.flat_manager.domain.model.entities.common.BaseEntityOwner;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Transactional
@Table(name = Payment.TABLE_NAME)
@SQLDelete(sql = Payment.SQL_UPDATE, check = ResultCheckStyle.COUNT)
@Where(clause = "state <> 'DELETED'")

public class Payment extends BaseEntityOwner {

    final static String TABLE_NAME = "payments";
    final static String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET state = 'DELETED' WHERE id = ?";

    @Column(name="payment_date", nullable = false)
    private LocalDate paymentDate;

    @Column(nullable = false)
    private Double amount;

    @NotNull
    @ManyToOne
    private Tenant tenant;

    @Override
    public String toString() {
        return "Payment{ id =" + getId() +
                " paymentDate=" + paymentDate +
                ", amount=" + amount +
                ", tenant=" + tenant +
                '}';
    }
}
