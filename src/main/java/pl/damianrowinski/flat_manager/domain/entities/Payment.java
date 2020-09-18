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
public class Payment extends BaseEntityLoggedUser {

    private LocalDate paymentIncomeDate;
    private Double amount;

    @ManyToOne
    private Tenant tenant;


}
