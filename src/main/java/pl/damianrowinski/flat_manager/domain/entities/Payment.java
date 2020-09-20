package pl.damianrowinski.flat_manager.domain.entities;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@Transactional
@ToString
@Table(name = Payment.TABLE_NAME)
public class Payment extends BaseEntityLoggedUser {

    final static String TABLE_NAME = "payments";

    @Column(name="payment_date", nullable = false)
    private LocalDate paymentDate;

    @NotNull
    private Double amount;

    @ManyToOne
    private Tenant tenant;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Payment payment = (Payment) o;
        return Objects.equals(paymentDate, payment.paymentDate) &&
                Objects.equals(amount, payment.amount) &&
                Objects.equals(tenant, payment.tenant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), paymentDate, amount, tenant);
    }

}
