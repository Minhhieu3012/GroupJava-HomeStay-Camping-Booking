package ut.edu.database.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import ut.edu.database.models.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
