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
@Table(name = Property.TABLE_NAME)
public class Property extends BaseEntityLoggedUser {

    final static String TABLE_NAME = "properties";

    @OneToMany(mappedBy = "property")
    private List<Room> rooms;

    @Column(nullable = false, name = "bills_rent")
    private Double billsRentAmount;

    @Column(nullable = false, name = "bills_utility")
    private Double billsUtilityAmount;

    @Column(nullable = false, name = "bills_payment_date")
    private LocalDate billsPaymentDate;

    @Override
    public String toString() {
        return "Property{ id= " + getId() +
                ", rent=" + billsRentAmount +
                ", billsAmount=" + billsUtilityAmount +
                ", paymentDate=" + billsPaymentDate +
                "}";
    }
}
