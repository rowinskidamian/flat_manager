package pl.damianrowinski.flat_manager.domain.entities;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Getter
@Setter
@Transactional
@Table(name = Room.TABLE_NAME)
public class Room extends BaseEntityLoggedUser {

    final static String TABLE_NAME = "rooms";

    @Column(nullable = false)
    private Double catalogRent;

    @NotNull
    @ManyToOne
    private Property property;

    @OneToOne
    private Tenant tenant;

    @Override
    public String toString() {
        return "Room{ id=" + getId() +
                ", catalogRent=" + catalogRent +
                ", property=" + property +
                ", tenant=" + tenant +
                '}';
    }
}
