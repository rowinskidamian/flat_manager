package pl.damianrowinski.flat_manager.domain.model.entities;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pl.damianrowinski.flat_manager.domain.model.entities.common.BaseEntityOwner;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Getter
@Setter
@Transactional
@Table(name = Room.TABLE_NAME)
@SQLDelete(sql = Room.SQL_UPDATE, check = ResultCheckStyle.COUNT)
@Where(clause = "state <> 'DELETED'")
public class Room extends BaseEntityOwner {

    final static String TABLE_NAME = "rooms";
    final static String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET state = 'DELETED' WHERE id = ?";

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
