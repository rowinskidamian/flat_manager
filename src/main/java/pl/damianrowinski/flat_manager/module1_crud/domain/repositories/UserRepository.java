package pl.damianrowinski.flat_manager.module1_crud.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.entities.User;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByLogin(String login);

    Optional<User> findFirstByNameContactEmail(String emailToCheck);

    Optional<User> findAllByLogin(String login);

}
