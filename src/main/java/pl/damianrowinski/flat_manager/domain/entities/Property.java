package pl.damianrowinski.flat_manager.domain.entities;

import lombok.Getter;
import lombok.Setter;
import pl.damianrowinski.flat_manager.model.common.Address;
import pl.damianrowinski.flat_manager.model.common.PersonNameContact;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Entity
@Getter
@Setter
@Transactional
@Table(name = Property.TABLE_NAME)
public class Property extends BaseEntityLoggedUser {

    final static String TABLE_NAME = "properties";

    @Column(nullable = false, name="working_name")
    private String workingName;

    @OneToMany(mappedBy = "property")
    private List<Room> rooms;

    @Column(nullable = false, name = "bills_rent")
    private Double billsRentAmount;

    @Column(nullable = false, name = "bills_utility")
    private Double billsUtilityAmount;

    @Column(nullable = false, name = "bills_payment_day")
    private Double billsPaymentDay;

    private Address address;
    private PersonNameContact ownerDetails;

    @Override
    public String toString() {
        return "Property{ id= " + getId() +
                ", rent=" + billsRentAmount +
                ", billsAmount=" + billsUtilityAmount +
                ", paymentDay=" + billsPaymentDay +
                ", address=" + address +
                ", ownerDetails=" + ownerDetails +
                "}";
    }
}
