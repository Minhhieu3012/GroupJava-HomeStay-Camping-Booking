package ut.edu.database.controllers.res;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import ut.edu.database.services.*;
import ut.edu.database.jwt.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final BookingService bookingService;
    private final PropertyService propertyService;
    private final ReportService reportService;
    private final ReviewService reviewService;

    // Xem profile admin
    @GetMapping("/profile")
    @PreAuthorize("hasRole('ADMIN')") //chi co nguoi dung co role admin moi goi duoc
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserDetails user, HttpServletRequest request) {
        //lay token tu header
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.extractUsername(token);
        String role = jwtUtil.extractRole(token).name();

        Map<String, String> profile = new HashMap<>();
        profile.put("email", email);
        profile.put("role", role);
        profile.put("token", token);

        return ResponseEntity.ok(profile);
    }

    // Thống kê hệ thống
    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getSystemStats() {
        //gom toan bo tong quan vao 1 API duy nhat cho admin dashboard
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userService.getAllUsers().size());
        stats.put("totalBookings", bookingService.getAllBookingDTOs().size());
        stats.put("totalProperties", propertyService.getAllPropertyDTOs().size());
        stats.put("totalReports", reportService.getAllReports().size());
        stats.put("totalReviews", reviewService.getAllReviews().size());

        return ResponseEntity.ok(stats);
    }

    // Danh sách role + quyền (hiển thị phân quyền hệ thống)
    @GetMapping("/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getRolesInfo() {
        Map<String, String[]> roles = new HashMap<>();
        roles.put("ADMIN", new String[]{"Quản lý toàn bộ hệ thống", "Xem + Xóa dữ liệu"});
        roles.put("OWNER", new String[]{"Tạo/Cập nhật homestay", "Xem booking của mình"});
        roles.put("CUSTOMER", new String[]{"Đặt phòng", "Gửi đánh giá", "Hủy booking của mình"});

        return ResponseEntity.ok(roles);
    }
}
