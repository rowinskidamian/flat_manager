package pl.damianrowinski.flat_manager.domain.model.entities.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.damianrowinski.flat_manager.utils.CurrentLocalDateTimeFormatted;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@MappedSuperclass
@Getter
@Setter
@ToString

public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private BaseEntityState state;

    @PrePersist
    public void addCreatedDate() {
        createdDate = CurrentLocalDateTimeFormatted.get();
        state = BaseEntityState.ACTIVE;
    }

    @PreUpdate
    public void updatedDate() {
        updatedDate = CurrentLocalDateTimeFormatted.get();
    }

    @PreRemove
    public void deleteAuthor() {
        this.state = BaseEntityState.DELETED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }



}
