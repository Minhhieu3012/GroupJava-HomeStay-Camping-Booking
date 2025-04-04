package ut.edu.database.controllers.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ut.edu.database.models.Role;
import ut.edu.database.dtos.LoginRequest;
import ut.edu.database.dtos.RegisterRequest;
import ut.edu.database.dtos.AuthResponse;
import ut.edu.database.models.User;
import ut.edu.database.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

@Service
public class AuthService {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    //register user
    public ResponseEntity<String> register(RegisterRequest registerRequest) {
        //kiem tra email da ton tai chua
        if(userService.existByEmail(registerRequest.getEmail())){
            return ResponseEntity.badRequest().body("Email Already Exist");
        }

        //ma hoa mk
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        //tao moi user
        Role role = Role.CUSTOMER; // hoặc Role.valueOf("CUSTOMER")
        User user = new User(registerRequest.getUsername(), registerRequest.getEmail(), encodedPassword, Role.CUSTOMER);
        userService.save(user);

        return ResponseEntity.ok("Successfully registered");
    }

    //login va tra ve JWT token
    public ResponseEntity<AuthResponse> login(LoginRequest loginRequest) {
        User user = userService.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Email not exist!!!"));

        //kiem tra mk
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            return ResponseEntity.badRequest().body(new AuthResponse("Incorrect Password"));
        }

        // Tạo JWT token và trả về
        String token = "JWT token se duoc tao tai day"; // Cần tích hợp logic tạo JWT ở đây
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
