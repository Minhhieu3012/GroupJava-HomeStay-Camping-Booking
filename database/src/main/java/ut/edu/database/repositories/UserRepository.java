package ut.edu.database.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import ut.edu.database.enums.Role;
import ut.edu.database.models.User;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
