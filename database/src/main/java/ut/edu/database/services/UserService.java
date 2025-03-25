package ut.edu.database.services;
import ut.edu.database.models.User;
import ut.edu.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
