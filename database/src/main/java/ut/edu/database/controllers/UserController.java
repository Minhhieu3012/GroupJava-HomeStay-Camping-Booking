package ut.edu.database.controllers;
import ut.edu.database.models.User;
import ut.edu.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ut.edu.database.services.UserService;
import java.util.List;
import java.util.Optional;

@RestController
//REST API
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    //lay tat ca user
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    //lay user theo id
//    @GetMapping("/{id}")


}
