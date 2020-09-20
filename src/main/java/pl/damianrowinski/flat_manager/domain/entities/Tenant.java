package pl.damianrowinski.flat_manager.domain.entities;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Transactional
@Table(name = Tenant.TABLE_NAME)
public class Tenant extends BaseEntityLoggedUser {

    final static String TABLE_NAME = "tenants";

    @Column(name = "lease_date_start", nullable = false)
    private LocalDate leaseDateStart;

    @Column(name = "lease_date_end", nullable = false)
    private LocalDate leaseDateEnd;

    @OneToOne
    private Room room;

    private Double rentDiscount;

    @Column(name = "payment_deadline", nullable = false)
    private LocalDate paymentDeadline;

    @Column(name = "account_payment_balance", nullable = false)
    private Double accountPaymentBalance;

    private PersonalDetails personalDetails;

    @OneToMany(mappedBy = "tenant")
    private List<Payment> paymentList;

}
