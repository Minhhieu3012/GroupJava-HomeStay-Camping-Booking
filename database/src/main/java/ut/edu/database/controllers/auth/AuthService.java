package ut.edu.database.controllers.auth;
//muc tieu:
//tach business logic cua Auth ra khoi controller
//gom 2 chuc nang:
//register -> dki user moi
//login -> kiem tra login va tra ve token

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
@RequiredArgsConstructor //tu tao constructor co chuc cac final field
public class AuthService {
    //3 dependency:
    private final UserRepository userRepository; //truy van user tu db
    private final PasswordEncoder passwordEncoder; //ma hoa/match password
    private final JwtUtil jwtUtil; //de tao token


    //Register User
    public ResponseEntity<String> register(RegisterRequest registerRequest) {
        //kiem tra email da ton tai chua
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            return ResponseEntity.badRequest().body("Email Already Exist");
        }

        //dung builder pattern de tao moi user
        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword())) //ma hoa password (bang BCrypt)
                .role(Role.CUSTOMER) // default role
                .build();

        //luu user vao db
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
