package ut.edu.database.services;
import org.springframework.beans.factory.annotation.Autowired;
import ut.edu.database.models.User;
import ut.edu.database.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
    //Constructor Injection
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //tim user theo email
    public Optional<User> findByEmail(String email) {
        if(email == null || email.isBlank()){
            throw new IllegalArgumentException("Invalid email");
        }
        return userRepository.findByEmail(email);
    }

    //lay ds all user
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    //lay user theo ID
    public Optional<User> getUserByID(Long userId){
        if(userId == null || userId <= 0){
            throw new IllegalArgumentException("Invalid user id");
        }
        return userRepository.findById(userId);
    }

    //Tao moi user
    public User createUser(User user) {
        if(user == null || user.getEmail() == null || user.getPassword() == null){
            throw new IllegalArgumentException("Invalid user");
        }
        if(existByEmail(user.getEmail())){
            throw new IllegalArgumentException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // ma hoa mat khau
        return userRepository.save(user);
    }
    //Update thong tin user
    public User updateUser(Long userId, User updatedUser) {
        Optional<User> existingUserOpt = userRepository.findById(userId);
        if(existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setRole(updatedUser.getRole());

            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword())); // Ma hoa lai mk
            }
            return userRepository.save(existingUser);
        }
        throw new IllegalArgumentException("User not found");
    }
    //Xoa user theo ID
    public void deleteUser(Long userId) {
        if(!userRepository.existsById(userId)){
            throw new IllegalArgumentException("User not found");
        }
        userRepository.deleteById(userId);
    }

    //kiem tra nguoi dung co ton tai bang email
    public boolean existByEmail(String email) {
        if(email == null || email.isBlank()){
            return false;
        }
        return userRepository.existsByEmail(email);
    }

    //lay ds user theo vai tro
    public List<User> getUsersByRole(String role) {
        if(role == null || role.isBlank()){
            throw new IllegalArgumentException("Invalid role");
        }
        return userRepository.findByRole(role);
    }

}
