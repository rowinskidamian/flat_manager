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
public class Tenant extends BaseEntityPersonalDetails{

    @NotNull
    private LocalDate leaseDateBeginning;
    @NotNull
    private LocalDate leaseDateEnding;

    @OneToOne
    private Room room;

    private Double rentDiscount;

    @NotNull
    private LocalDate paymentDeadline;
    @NotNull
    private Double accountPaymentBalance;

    @OneToMany(mappedBy = "tenant")
    private List<Payment> paymentList;

}
