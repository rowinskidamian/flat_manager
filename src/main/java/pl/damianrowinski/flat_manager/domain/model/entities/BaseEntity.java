package pl.damianrowinski.flat_manager.domain.model.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private EntityState state;

    @PrePersist
    public void addCreatedDate() {
        createdDate = LocalDateTime.now();
        state = EntityState.ACTIVE;
    }

    @PreUpdate
    public void updatedDate() {
        updatedDate = LocalDateTime.now();
    }

    @PreRemove
    public void deleteAuthor() {
        this.state = EntityState.DELETED;
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
