package ut.edu.database.controllers.auth;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ut.edu.database.dtos.AuthResponse;
import ut.edu.database.dtos.LoginRequest;
import ut.edu.database.dtos.RegisterRequest;
import ut.edu.database.enums.Role;
import ut.edu.database.jwt.JwtUtil;
import ut.edu.database.models.User;
import ut.edu.database.repositories.UserRepository;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    //Register User
    public ResponseEntity<String> register(RegisterRequest registerRequest) {
        //kiem tra email da ton tai chua
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            return ResponseEntity.badRequest().body("Email Already Exist");
        }

//        //ma hoa mk
//        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
//        //tao moi user
//        Role role = Role.CUSTOMER; // hoặc Role.valueOf("CUSTOMER")
//        User user = new User(registerRequest.getUsername(), registerRequest.getEmail(), encodedPassword, Role.CUSTOMER);

        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.CUSTOMER) // default role
                .build();

        userRepository.save(user);
        return ResponseEntity.ok("Successfully registered");
    }

    //Login User va tra ve JWT token
    public ResponseEntity<AuthResponse> login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Email not exist!!!"));

        //kiem tra mk
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            return ResponseEntity.badRequest().body(new AuthResponse("Incorrect Password"));
        }

        // Tạo JWT token bằng JwtUtil
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        return ResponseEntity.ok(new AuthResponse(token));
    }
}
