package pl.damianrowinski.flat_manager.domain.entities;

import pl.damianrowinski.flat_manager.domain.entities.Payment;
import pl.damianrowinski.flat_manager.domain.entities.PersonalDetails;
import pl.damianrowinski.flat_manager.domain.entities.Room;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
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
}
