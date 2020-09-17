package pl.damianrowinski.flat_manager.domain.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private LocalDate paymentIncomeDate;

    private Double amount;

    @ManyToOne
    private Tenant tenant;

}
