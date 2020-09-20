package pl.damianrowinski.flat_manager.domain.entities;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Transactional
@Table(name = Property.TABLE_NAME)
public class Property extends BaseEntityLoggedUser {

    final static String TABLE_NAME = "properties";

    @OneToMany (mappedBy = "property")
    private List<Room> rooms;

    @NotNull
    private Double rent;

    @NotNull
    private Double billsAmount;

    @NotNull
    private LocalDate paymentDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Property property = (Property) o;
        return Objects.equals(rent, property.rent) &&
                Objects.equals(billsAmount, property.billsAmount) &&
                Objects.equals(paymentDate, property.paymentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rent, billsAmount, paymentDate);
    }

    @Override
    public String toString() {
        return "Property{" +
                "rent=" + rent +
                ", billsAmount=" + billsAmount +
                ", paymentDate=" + paymentDate +
                '}';
    }
}
