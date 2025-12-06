package pl.damianrowinski.flat_manager.domain.model.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pl.damianrowinski.flat_manager.domain.model.common.Address;
import pl.damianrowinski.flat_manager.domain.model.entities.common.BaseEntityOwner;
import pl.damianrowinski.flat_manager.domain.model.common.PersonNameContact;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Transactional
@Table(name = Tenant.TABLE_NAME)
@SQLDelete(sql = Tenant.SQL_UPDATE, check = ResultCheckStyle.COUNT)
@Where(clause = "state <> 'DELETED'")

public class Tenant extends BaseEntityOwner {

    final static String TABLE_NAME = "tenants";
    final static String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET state = 'DELETED' WHERE id = ?";

    @Column(name = "lease_date_start", nullable = false)
    private LocalDate leaseDateStart;

    @Column(name = "lease_date_end", nullable = false)
    private LocalDate leaseDateEnd;

    @Column(name = "rent_discount", nullable = true)
    private Double rentDiscount;

    @Column(name = "rent_payment_deadline", nullable = false)
    private Integer paymentDeadline;

    @OneToOne(mappedBy = "tenant")
    private Room room;

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.REMOVE)
    private List<Payment> paymentList;

    private PersonNameContact personalDetails;

    private Address contactAddress;

    @Override
    public String toString() {
        String roomDetails = room != null ? ", room = " + room.getId() : ", tenant without room";
        return "Tenant{ id " + getId() +
                ", leaseDateStart=" + leaseDateStart +
                ", leaseDateEnd=" + leaseDateEnd +
                ", rentDiscount=" + rentDiscount +
                ", paymentDeadline=" + paymentDeadline +
                ", personalDetails=" + personalDetails +
                roomDetails +
                ", address=" + contactAddress +
                '}';
    }
}
