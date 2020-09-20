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

    @NotNull
    private Double catalogRent;

    @ManyToOne
    private Property property;

    @OneToOne
    private Tenant tenant;
}
