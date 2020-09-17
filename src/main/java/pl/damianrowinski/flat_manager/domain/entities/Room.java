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
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Double rent;

    @ManyToOne
    private Property property;

    @OneToOne
    private Tenant tenant;

    @ManyToOne
    @NotNull
    private User user;
}
