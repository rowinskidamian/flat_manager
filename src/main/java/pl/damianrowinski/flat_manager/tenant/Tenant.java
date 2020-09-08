package pl.damianrowinski.flat_manager.tenant;

import pl.damianrowinski.flat_manager.payment.Payment;
import pl.damianrowinski.flat_manager.personal_details.PersonalDetails;
import pl.damianrowinski.flat_manager.room.Room;

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
