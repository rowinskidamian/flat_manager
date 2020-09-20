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

    @Column(nullable = false)
    private Double rent;

    @Column(nullable = false)
    private Double billsAmount;

    @Column(nullable = false)
    private LocalDate paymentDate;

    @Override
    public String toString() {
        return "Property{ id= " + getId() +
                ", rent=" + rent +
                ", billsAmount=" + billsAmount +
                ", paymentDate=" + paymentDate +
                "}";
    }
}
