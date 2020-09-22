package pl.damianrowinski.flat_manager.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.damianrowinski.flat_manager.domain.entities.Tenant;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface TenantRepository extends JpaRepository<Tenant, Long> {
}
