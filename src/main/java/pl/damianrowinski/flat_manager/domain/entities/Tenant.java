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
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    private PersonalDetails personalDetails;

    private LocalDate leaseDateBeginning;

    private LocalDate leaseDateEnding;

    @OneToOne
    private Room room;

    private Double rent;

    private LocalDate paymentDeadline;

    private Double accountPaymentBalance;

    @OneToMany(mappedBy = "tenant")
    private List<Payment> paymentList;

    @ManyToOne
    @NotNull
    private User user;
}
