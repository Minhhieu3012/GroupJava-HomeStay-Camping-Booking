package ut.edu.database.controllers;
import ut.edu.database.models.User;
import ut.edu.database.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
//REST API
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    //lay tat ca user
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    //lay user theo id
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id);
    }
    //tao user moi
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
    //update thong tin user
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userRepository.save(user);
    }
    //delete user
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
