package pl.damianrowinski.flat_manager.domain.entities;

import lombok.Getter;
import lombok.Setter;
import pl.damianrowinski.flat_manager.model.common.Address;
import pl.damianrowinski.flat_manager.model.common.PersonNameContact;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDate;

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

    @OneToOne(mappedBy = "tenant")
    private Room room;

    @Column(name = "rent_discount", nullable = true)
    private Double rentDiscount;

    @Column(name = "rent_payment_deadline", nullable = false)
    private LocalDate paymentDeadline;

    private PersonNameContact personalDetails;

    private Address contactAddress;

    @Override
    public String toString() {
        return "Tenant{ id " + getId() +
                ", leaseDateStart=" + leaseDateStart +
                ", leaseDateEnd=" + leaseDateEnd +
                ", room=" + room.getId() +
                ", rentDiscount=" + rentDiscount +
                ", paymentDeadline=" + paymentDeadline +
                ", personalDetails=" + personalDetails +
                ", address=" + contactAddress +
                '}';
    }
}
