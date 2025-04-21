//package admin.bookinghomecamping.controller;
//
//import admin.bookinghomecamping.dto.UserDTO;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller
//@RequestMapping("/admin")
//public class AdminUserManagementController {
//
//    private List<UserDTO> mockUsers = new ArrayList<>();
//
//    public AdminUserManagementController() {
//        // Tạo mock dữ liệu
//        mockUsers.add(new UserDTO(1L, "Ngô Gia Cò", "a@gmail.com", "0123456789", "HOST", true));
//        mockUsers.add(new UserDTO(2L, "Phan Minh MU", "b@gmail.com", "0987654321", "HOST", false));
//        mockUsers.add(new UserDTO(3L, "Lê Ngô Hữu Kền", "c@gmail.com", "0778899001", "USER", true));
//    }
//
//    @GetMapping("/user-management")
//    public String showUserManagementPage(Model model) {
//        model.addAttribute("users", mockUsers);
//        return "bookingHomeCamping/QuanLiTKChuHomestay-NguoiDung"; // trỏ đến file HTML bạn gửi ở trên
//    }
//
//    @PostMapping("/user/toggle-status")
//    public String toggleUserStatus(@RequestParam Long userId) {
//        for (UserDTO user : mockUsers) {
//            if (user.getId().equals(userId)) {
//                user.setActive(!user.isActive());
//                break;
//            }
//        }
//        return "redirect:/admin/user-management";
//    }
//}
