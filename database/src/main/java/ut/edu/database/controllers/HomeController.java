//Thường để xử lí route gốc hoặc tài nguyên public (/api/home, /api)
package ut.edu.database.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ut.edu.database.dtos.*;
import ut.edu.database.enums.PropertyStatus;
import ut.edu.database.enums.Role;
import ut.edu.database.mapper.PropertyMapper;
import ut.edu.database.models.Property;
import ut.edu.database.models.User;
import ut.edu.database.repositories.PropertyRepository;
import ut.edu.database.repositories.UserRepository;
import ut.edu.database.services.BookingService;
import ut.edu.database.services.PropertyService;
import ut.edu.database.services.ReportService;
import ut.edu.database.services.UserService;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.util.StringUtils;
import java.util.Optional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class HomeController {
    @Autowired
    private final ReportService reportService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private PropertyMapper propertyMapper;
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BookingService bookingService;

    @GetMapping("/layout")
    public String layoutPage() {
        return "master/_layout";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "index";
    }

    @GetMapping("/home")
    public String HomePage() {
        return "home";//goi den html page
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "home";//goi den html page
    }


    private final ReportService ReportService;

    @GetMapping("/bao-cao-doanh-thu")
    public String viewRevenueReport(Model model) {
//        List<ReportDTO> monthlyReports = ReportService.getAllReports(); // Lấy danh sách báo cáo
        List<MonthlyRevenueDTO> monthlyRevenues = ReportService.getMonthlyRevenue(2025, null, true);

        // Tạo map tháng => doanh thu
        Map<Integer, BigDecimal> revenueByMonth = new HashMap<>();
        for (MonthlyRevenueDTO dto : monthlyRevenues) {
            revenueByMonth.put(dto.getMonth(), dto.getTotalRevenue());
        }

        List<String> months = new ArrayList<>();
        List<BigDecimal> revenues = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            months.add(String.format("%02d/2025", month));
            revenues.add(revenueByMonth.getOrDefault(month, BigDecimal.ZERO));
        }

        model.addAttribute("months", months);
        model.addAttribute("revenues", revenues);
        model.addAttribute("totalRevenue", reportService.calculateTotalRevenue());
        model.addAttribute("managementFee", reportService.calculateManagementFee());
        model.addAttribute("occupancyRate", reportService.calculateOccupancyRate());
        model.addAttribute("monthlyRevenues", monthlyRevenues);
        return "bookingHomeCamping-admin/BaoCaoDoanhThu";
    }


    @GetMapping("/quan-li-phi-dich-vu")
    public String quanliphidichvuPage() {
        return "bookingHomeCamping-admin/QuanLiPhiDichVu";//goi den html page
    }

//XEM DS CUSTOMER
    @GetMapping("/quan-li-tk-user")
    public String quanlitknguoidungPage(Model model) {
//        List<UserDTO> userList = userService.getAllUsers();
        List<UserDTO> userList = userService.getAllCustomerUsers();
        model.addAttribute("userList", userList);
        return "bookingHomeCamping-admin/QuanLiTKNguoiDung";//goi den html page
    }

    //CHUYỂN SANG TRANG CHỈNH SỬA CUS
    @GetMapping("/quan-li-tk-user/chinh-sua-tk-user/{id}")
    public String chinhSuaTaiKhoanCus(@PathVariable Long id, Model model) {
        Optional<User> userDTO = userService.getUserById(id); // Lấy user theo id
        User user = userDTO.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng với ID: " + id));
        model.addAttribute("user", user); // Truyền đối tượng User thay vì Optional<User>
        model.addAttribute("id", id); // Thêm id vào model để sử dụng trong form
        model.addAttribute("username", user.getUsername()); // Thêm các thuộc tính khác
        model.addAttribute("email", user.getEmail());
        model.addAttribute("phone", user.getPhone());
        model.addAttribute("identityCard", user.getIdentityCard());
        return "bookingHomeCamping-admin/ChinhSuaTKNguoiDung"; // Đúng tên file HTML
    }

    //LƯU THÔNG TIN THAY ĐỔI VÀ QUAY LẠI TRANG DANH SÁCH CUS
    @PostMapping("/cap-nhat-tk-user")
    public String updateUserAccount(@RequestParam("id") Long id,
                                    @RequestParam("username") String username,
                                    @RequestParam("email") String email,
                                    @RequestParam("phone") String phone,
                                    @RequestParam("identityCard") String identityCard,
                                    @RequestParam(value = "avatar", required = false) String avatar,
                                    @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
                                    Model model) {

        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(username);
            user.setEmail(email);
            user.setPhone(phone);
            user.setIdentityCard(identityCard);

            // Xử lý upload ảnh mới nếu có
            if (avatarFile != null && !avatarFile.isEmpty()) {
                String fileName = StringUtils.cleanPath(avatarFile.getOriginalFilename());
                String uploadDir = "static/static-user/img/avt users/uploaded/";

                try {
                    // Tạo thư mục nếu chưa tồn tại
                    Path uploadPath = Paths.get(uploadDir);
                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }

                    // Tạo tên file duy nhất để tránh trùng lặp
                    String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
                    Path filePath = uploadPath.resolve(uniqueFileName);

                    // Lưu file
                    Files.copy(avatarFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                    // Cập nhật đường dẫn ảnh cho user
                    user.setAvatar("/" + uploadDir + uniqueFileName);

                } catch (IOException e) {
                    e.printStackTrace();
                    model.addAttribute("errorMessage", "Lỗi khi tải lên ảnh: " + e.getMessage());
                    return "redirect:/quan-li-tk-user";
                }
            }
            // Nếu người dùng chọn avatar có sẵn
            else if (avatar != null && !avatar.isEmpty()) {
                user.setAvatar(avatar);
            }

            UserDTO userDTO = new UserDTO();
            userDTO.setId(id);
            userDTO.setUsername(username);
            userDTO.setEmail(email);
            userDTO.setPhone(phone);
            userDTO.setIdentityCard(identityCard);
            userDTO.setAvatar(user.getAvatar()); // Dùng avatar đã cập nhật từ user object
            userService.updateUser(userDTO);
            model.addAttribute("successMessage", "Cập nhật tài khoản thành công!");
        } else {
            model.addAttribute("errorMessage", "Không tìm thấy người dùng!");
        }

        return "redirect:/quan-li-tk-user";
    }

    //XÓA CUS
    @GetMapping("/xoa-user/{id}")
    public String xoaUser(@PathVariable Long id) {
        userService.deleteUser(id); // gọi service để xóa
        return "redirect:/quan-li-tk-user"; // xóa xong quay lại danh sách
    }

    //XEM DS OWNER
    @GetMapping("/quan-li-tk-owner")
    public String quanlitkchuhomestayPage(Model model) {
        List<UserDTO> ownerList = userService.getAllOwnerUsers();
        model.addAttribute("ownerList", ownerList);
        return "bookingHomeCamping-admin/QuanLiTKChuHomestay";//goi den html page
    }

    //CHUYỂN SANG TRANG CHỈNH SỬA OWNER
    @GetMapping("/quan-li-tk-owner/chinh-sua-tk-owner/{id}")
    public String chinhSuaTaiKhoanOwner(@PathVariable Long id, Model model) {
        Optional<User> ownerDTO = userService.getUserById(id); // lấy user theo id
        model.addAttribute("owner", ownerDTO);
        return "bookingHomeCamping-admin/ChinhSuaTKChuHomestay"; // đúng tên file HTML
    }

    //LƯU THÔNG TIN THAY ĐỔI VÀ QUAY LẠI TRANG DANH SÁCH OWNER
    @PostMapping("/cap-nhat-tk-owner")
    public String capNhatTaiKhoanOwner(@ModelAttribute UserDTO userDTO) {
        userService.updateUser(userDTO); // gọi service cập nhật
        return "redirect:/quan-li-tk-owner"; // quay lại danh sách tài khoản
    }

    //XÓA OWNER
    @GetMapping("/xoa-owner/{id}")
    public String xoaOwner(@PathVariable Long id) {
        userService.deleteOwner(id); // gọi service để xóa
        return "redirect:/quan-li-tk-owner"; // xóa xong quay lại danh sách
    }


    //NHẤP VÀO THÊM PHÒNG
    @GetMapping("/them-phong")
    public String themphongPage(Model model) {
        model.addAttribute("propertyDTO", new PropertyDTO());
        return "bookingHomeCamping-admin/ThemPhong";//goi den html page
    }

// NHẤP VÀO LƯU SAU KHI NHẬP PHÒNG CẦN THÊM
    @PostMapping("/luu-phong")
    public String saveNewProperty(@ModelAttribute PropertyDTO propertyDTO,
                                  @RequestParam("imageFile") MultipartFile imageFile,
                                  Model model)
    {
        Property property = propertyMapper.toEntity(propertyDTO);

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String filename = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                String uploadDir = new ClassPathResource("static/properties").getFile().getAbsolutePath();
                File saveFile = new File(uploadDir + File.separator + filename);
                imageFile.transferTo(saveFile);

                //Lưu đường dẫn ảnh (loại bỏ "/static")
                property.setImage("/properties/" + filename);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Gán owner nếu có
        if (propertyDTO.getOwner_id() != null) {
            Optional<User> ownerOpt = userRepository.findById(propertyDTO.getOwner_id());
            if (ownerOpt.isPresent()) {
                User owner = ownerOpt.get();
                if (owner.getRole() != Role.OWNER) {
                    // Nếu user không phải chủ phòng, trả lại form và báo lỗi
                    model.addAttribute("errorMessage", "Người dùng ID " + owner.getId() + " không phải Chủ phòng (OWNER)!");
                    return "bookingHomeCamping-admin/ThemPhong"; // <- tên file html form thêm phòng
                }
                property.setOwner(owner);
            } else {
                model.addAttribute("errorMessage", "Không tìm thấy chủ phòng với ID: " + propertyDTO.getOwner_id());
                return "bookingHomeCamping-admin/ThemPhong";
            }
        } else {
            model.addAttribute("errorMessage", "Thiếu Owner ID!");
            return "bookingHomeCamping-admin/ThemPhong";
        }

        // Set trạng thái mặc định nếu chưa có
        if (property.getStatus() == null) {
            property.setStatus(PropertyStatus.AVAILABLE);
        }

        propertyRepository.save(property);

        return "redirect:/xem-phong";
    }

    //    private final PropertyService propertyService; // inject service vào
    @GetMapping("/xem-phong")
    public String xemphongPage(Model model) {
        List<PropertyDTO> roomList = propertyService.getAllPropertyDTOs();
        model.addAttribute("roomList", roomList);
        return "bookingHomeCamping-admin/XemPhong";//goi den html page
    }

//NHẤP VÀO CHỈNH SỬA
    @GetMapping("/chinh-sua-phong/{id}")
    public String chinhsuaphongPage(@PathVariable Long id, Model model) {
        Optional<PropertyDTO> optional = propertyService.getPropertyDTOById(id);
        if (optional.isPresent()) {
            PropertyDTO property = optional.get();
            model.addAttribute("property", property);
            model.addAttribute("image", property.getImage()); // nếu bạn cần ảnh hiện tại
            return "bookingHomeCamping-admin/ChinhSuaPhong";
        } else {
            // Có thể redirect hoặc thông báo lỗi
            return "redirect:/xem-phong?error=notfound";
        }
    }

//NHẤP VÀO NÚT CẬP NHẬT
    @PostMapping("/chinh-sua-phong")
    public String updatePhong(@ModelAttribute("property") PropertyDTO dto,
                              @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        propertyService.updatePropertyWithImage(dto, imageFile);
        return "redirect:/xem-phong";
    }

//    XÓA PHÒNG
    @GetMapping("/xoa-phong/{id}")
    public String xoaPhong(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return "redirect:/xem-phong"; // quay về danh sách sau khi xóa
    }

    @GetMapping("/hoa-don")
    public String hoadonPage() {
        return "bookingHomeCamping-admin/HoaDon";//goi den html page
    }

    @GetMapping("/don-dat-phong")
    public String dondatphongPage(Model model) {
        List<BookingDTO> bookings = bookingService.getAllBookingDTOs();
        model.addAttribute("bookings", bookings);
        return "bookingHomeCamping-admin/DonDatPhong";//goi den html page
    }

}

//Layout ...
