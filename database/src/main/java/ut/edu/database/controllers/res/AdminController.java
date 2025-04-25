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
import java.util.LinkedHashMap;
import java.util.List;
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
    public ResponseEntity<Map<String, List<String>>> getRolesInfo() {
        Map<String, List<String>> roles = new LinkedHashMap<>();

        roles.put("ADMIN", List.of(
                "Quản lý toàn bộ hệ thống",
                "Phân quyền người dùng",
                "Xem, tạo, chỉnh sửa, xóa tất cả dữ liệu",
                "Xem báo cáo doanh thu toàn hệ thống",
                "Quản lý tài khoản OWNER và CUSTOMER",
                "Duyệt báo cáo và phản hồi người dùng"
        ));

        roles.put("OWNER", List.of(
                "Tạo/Cập nhật thông tin homestay hoặc khu cắm trại",
                "Quản lý danh sách phòng (thêm/sửa/xem)",
                "Tải ảnh và thông tin mô tả phòng",
                "Xem đơn đặt phòng dành cho homestay của mình",
                "Xem báo cáo doanh thu riêng của homestay",
                "Phản hồi đánh giá từ khách hàng"
        ));

        roles.put("CUSTOMER", List.of(
                "Tìm kiếm và đặt phòng (homestay hoặc camping)",
                "Chọn dịch vụ bổ sung (combo ăn uống...)",
                "Thanh toán và xem lịch sử đơn hàng",
                "Gửi đánh giá sau khi sử dụng dịch vụ",
                "Hủy đơn đặt phòng nếu cần",
                "Quản lý thông tin cá nhân và avatar"
        ));

        return ResponseEntity.ok(roles);
    }

}
