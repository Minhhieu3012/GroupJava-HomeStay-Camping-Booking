package ut.edu.database.services;
import ut.edu.database.models.User;
import ut.edu.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //tim user theo email
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    //lay ds all user
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    //lay user theo ID
    public Optional<User> getUserByID(Long userId){
        return userRepository.findById(userId);
    }
    //Tao moi user
    public User createUser(User user) {
        return userRepository.save(user);
    }
    //Update thong tin user
    public User updateUser(Long userId, User updatedUser) {
        Optional<User> existingUserOpt = userRepository.findById(userId);
        if(existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setRole(updatedUser.getRole());
            return userRepository.save(existingUser);
        }
        return null;
    }
    //Xoa user theo ID
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
    //kiem tra nguoi dung co ton tai bang email
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    //
    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

}
