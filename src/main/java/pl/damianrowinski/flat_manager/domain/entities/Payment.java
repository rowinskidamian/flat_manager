package pl.damianrowinski.flat_manager.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Transactional
@Table(name = Payment.TABLE_NAME)
public class Payment extends BaseEntityLoggedUser {

    final static String TABLE_NAME = "payments";

    @Column(name="payment_date", nullable = false)
    private LocalDate paymentDate;

    @Column(nullable = false)
    private Double amount;

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
