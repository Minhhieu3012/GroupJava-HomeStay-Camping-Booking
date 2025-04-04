package ut.edu.database.controllers.res;

import ut.edu.database.controllers.auth.AuthService;
import ut.edu.database.dtos.LoginRequest;
import ut.edu.database.dtos.RegisterRequest;
import ut.edu.database.dtos.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//xu li cac yeu cau sign up, login, tra ve jwt token cho user
@RestController
@RequestMapping("/api/auth")
public class AuthsController {
    @Autowired
    private AuthService authService;

    //register user moi
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    //login va lay token
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
