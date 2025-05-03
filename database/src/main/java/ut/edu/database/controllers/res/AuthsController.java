package ut.edu.database.controllers.res;
//Muc tieu:
//Dang ky user moi
//Dang nhap va nhan token
//lay thong tin profile tu token

import java.util.stream.Collectors;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import ut.edu.database.dtos.AuthResponse;
import ut.edu.database.dtos.LoginRequest;
import ut.edu.database.dtos.RegisterRequest;
import ut.edu.database.enums.Role;
import ut.edu.database.jwt.JwtService;
import ut.edu.database.jwt.JwtUtil;
import ut.edu.database.models.User;
import ut.edu.database.repositories.UserRepository;
import ut.edu.database.services.UserService;

@RestController //tra ve JSON thay vi HTML
@RequestMapping("/api/auths") //prefix cho moi endpoint
@RequiredArgsConstructor //tao constructor co chuc cac final field
public class AuthsController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/auth/process-login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpServletResponse response) {
        try {
            // Xác thực người dùng
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // Lưu vào SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Tạo JWT token
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(userDetails);

            // Lưu JWT vào cookie
            Cookie jwtCookie = new Cookie("jwt", token);
            jwtCookie.setPath("/");
            jwtCookie.setHttpOnly(true); // Bảo mật hơn, không cho JS truy cập
            jwtCookie.setMaxAge(24 * 60 * 60); // 1 ngày
            response.addCookie(jwtCookie);

            return "redirect:/"; // Chuyển về trang chủ sau khi đăng nhập
        } catch (Exception e) {
            return "redirect:/auth/login-user?error";
        }
    }

    //dang nhap
    @PostMapping(value= "/login", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            //luong xu li - xac thuc dang nhap
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            //lay thong tin user tu DB
            UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
            //lay vai tro
            String role = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .map(r -> r.replace("ROLE_", "")) // Bỏ tiền tố "ROLE_"
                    .collect(Collectors.joining(","));
            //Sinh token
            String token = jwtUtil.generateToken(userDetails.getUsername(), Role.valueOf(role));

            // Trả về đầy đủ token, username, role
            return ResponseEntity.ok(new AuthResponse(token, userDetails.getUsername(), role));
        } catch (AuthenticationException e) { //loi 401
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai thông tin đăng nhập :((");
        }
    }

    //dang ky
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest RegisterRequest) {
        String result = userService.registerUser(RegisterRequest);
        return ResponseEntity.ok(result);
    }

    //xem profile
    //Spring tu inject Authentication obj neu ban da login thanh cong va token hop le
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication authentication) {
        try {
            //kiem tra login
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            //Lay username an toàn
            String username = authentication.getName();

            //Truy van DB
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Không tìm thấy username: " + username
                    ));

            //Tra ve du lieu
            return ResponseEntity.ok(new UserProfileResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getIdentityCard(),
                    user.getPassword(),
                    user.getRole().toString()
            ));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Lỗi khi truy xuất hồ sơ người dùng :((");
        }
    }

    //Giup tao class nho gon cho response
    record UserProfileResponse(Long id, String username, String email, String phone, String identityCard, String password ,String role) {}
}

