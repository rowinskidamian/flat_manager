package pl.damianrowinski.flat_manager.room;

import pl.damianrowinski.flat_manager.property.Property;
import pl.damianrowinski.flat_manager.tenant.Tenant;

import javax.persistence.*;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Double rent;

    @ManyToOne
    private Property property;

    @OneToOne
    private Tenant tenant;
}
