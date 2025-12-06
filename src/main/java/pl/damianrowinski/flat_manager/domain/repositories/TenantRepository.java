package pl.damianrowinski.flat_manager.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.damianrowinski.flat_manager.domain.model.entities.Tenant;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    List<Tenant> findAllByLoggedUserName(String loggedUserName);

    List<Tenant> findAllByLoggedUserNameAndRoomIsNull(String loggedUserName);

    Optional<Tenant> findFirstByPersonalDetailsEmail(String email);

    @Query(value = "SELECT COUNT(t.id) FROM Tenant t WHERE t.loggedUserName = ?1")
    long findNoOfUserTenants(String loggedUserName);

}
