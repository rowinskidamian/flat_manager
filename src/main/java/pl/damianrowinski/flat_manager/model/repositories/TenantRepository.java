package pl.damianrowinski.flat_manager.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.damianrowinski.flat_manager.domain.entities.Tenant;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    @Query(value = "SELECT * FROM tenants WHERE logged_user_name = ?1 AND room_id IS NULL", nativeQuery = true)
    List<Tenant> findByLoggedUserWithNoRoom(String LoggedUserName);

}
