package ut.edu.database.controllers.res;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import ut.edu.database.jwt.JwtUtil;
import ut.edu.database.services.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @GetMapping("/profile")
    public String getUserProfile(HttpServletRequest request) {
        // Lấy token từ header Authorization
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return "Missing or invalid token!";
        }

        // Cắt bỏ "Bearer " để lấy token thực sự
        String token = authHeader.substring(7);
        String role  = String.valueOf(jwtUtil.extractRole(token));
        return "Token: " + token; // Test xem có nhận được token không
    }
}
