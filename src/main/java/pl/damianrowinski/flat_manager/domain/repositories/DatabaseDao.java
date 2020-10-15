package pl.damianrowinski.flat_manager.domain.repositories;


import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class DatabaseDao {
    @PersistenceContext
    EntityManager entityManager;

    public void clearDatabase() {
        List<String> sqlQueries = List.of("DELETE FROM payments",
                "DELETE FROM rooms",
                "DELETE FROM tenants",
                "DELETE FROM properties",
                "DELETE FROM payment_balance");

        sqlQueries.forEach(query -> {
            Query nativeQuery = entityManager.createNativeQuery(query);
            nativeQuery.executeUpdate();
        });


    }
}
