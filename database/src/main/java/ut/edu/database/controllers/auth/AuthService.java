package ut.edu.database.controllers.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ut.edu.database.dtos.LoginRequest;
import ut.edu.database.dtos.RegisterRequest;
import ut.edu.database.dtos.AuthResponse;
import ut.edu.database.models.User;
import ut.edu.database.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

@Service
public class AuthService {
    @Autowired
    private UserService userService; //service de thao tac voi user

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; //dung de ma hoa mk

    //register user
    public ResponseEntity<String> register(RegisterRequest registerRequest) {
        //kiem tra email da ton tai chua
        if(userService.existByEmail(registerRequest.getEmail())){
            return ResponseEntity.badRequest().body("Email Already Exist");
        }

        //ma hoa mk
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        //tao moi user
        User user = new User(registerRequest.getEmail(), encodedPassword, "ROLE_USER");
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
        //tao jwt token va tra ve
        String token = "JWT token se duoc tao tai day"; //can tich hop logic tao token
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
