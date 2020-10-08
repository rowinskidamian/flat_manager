package pl.damianrowinski.flat_manager.domain.model.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pl.damianrowinski.flat_manager.domain.model.common.Address;
import pl.damianrowinski.flat_manager.domain.model.entities.common.BaseEntityOwner;
import pl.damianrowinski.flat_manager.domain.model.common.PersonNameContact;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Entity
@Getter
@Setter
@Transactional
@Table(name = Property.TABLE_NAME)
@SQLDelete(sql = Property.SQL_UPDATE, check = ResultCheckStyle.COUNT)
@Where(clause = "state <> 'DELETED'")
public class Property extends BaseEntityOwner {

    final static String TABLE_NAME = "properties";
    final static String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET state = 'DELETED' WHERE id = ?";


    @Column(nullable = false, name="working_name")
    private String workingName;

    @OneToMany(mappedBy = "property")
    private List<Room> rooms;

    @Column(nullable = false, name = "bills_rent")
    private Double billsRentAmount;

    @Column(nullable = false, name = "bills_utility")
    private Double billsUtilityAmount;

    @Column(nullable = false, name = "bills_payment_day")
    private Integer billsPaymentDay;

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
