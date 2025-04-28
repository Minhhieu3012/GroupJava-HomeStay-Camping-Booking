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
import ut.edu.database.services.*;
import ut.edu.database.services.PaymentService;

import java.io.File;
import java.util.*;

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
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/layout")
    public String layoutPage() {
        return "master/_layout";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "index";
    }

    @GetMapping("/home")
    public String homePage() {
        return "home"; // Không thêm .html
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "home";//goi den html page
    }


    private final ReportService ReportService;

////XEM BAO CAO DOANH THU

    @GetMapping("/bao-cao-doanh-thu")
    public String viewRevenueReport(Model model) {
        int year = 2025;
        List<PaymentDTO> payments = paymentService.getAllPayments(); // hoặc getAllPaymentsForAdmin();
        List<MonthlyRevenueDTO> monthlyRevenues = paymentService.calculateMonthlyRevenue(year);

        double totalRevenue = reportService.calculateTotalRevenue(payments);
        double managementFee = reportService.calculateManagementFee(payments);
        double occupancyRate = reportService.calculateTotalRevenue;// bạn tính thêm nếu cần


        model.addAttribute("monthlyRevenues", monthlyRevenues);
        model.addAttribute("payments", payments);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("managementFee", managementFee);
        model.addAttribute("occupancyRate", occupancyRate);
        return "bookingHomeCamping-admin/BaoCaoDoanhThu";
    }


    @GetMapping("/quan-li-phi-dich-vu")
    public String quanliphidichvuPage(Model model) {
        List<PaymentDTO> payments = paymentService.getAllPayments();
        model.addAttribute("payments", payments);
        return "bookingHomeCamping-admin/QuanLiPhiDichVu";//goi den html page
    }

    @GetMapping("/quan-li-phi-dich-vu/{paymentId}")
    public String viewPaymentDetail(@PathVariable Long paymentId, Model model) {
        PaymentDTO payment = paymentService.getPaymentById(paymentId);
        model.addAttribute("payment", payment);
        return "bookingHomeCamping-admin/ChiTietHoaDon"; // Gọi trang chi tiết
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
        Optional<User> userDTO = userService.getUserById(id); // lấy user theo id
        model.addAttribute("user", userDTO);
        return "bookingHomeCamping-admin/ChinhSuaTKNguoiDung"; // đúng tên file HTML
    }

    //LƯU THÔNG TIN THAY ĐỔI VÀ QUAY LẠI TRANG DANH SÁCH CUS
    @PostMapping("/cap-nhat-tk-user")
    public String capNhatTaiKhoanCus(@ModelAttribute UserDTO userDTO) {
        userService.updateUser(userDTO); // gọi service cập nhật
        return "redirect:/quan-li-tk-user"; // quay lại danh sách tài khoản
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
    public String hoadonPage(Model model) {
        List<PaymentDTO> payments = paymentService.getAllPayments();
        model.addAttribute("payments", payments);
        return "bookingHomeCamping-admin/HoaDon";
    }

    @GetMapping("/don-dat-phong")
    public String dondatphongPage(Model model) {
        List<BookingDTO> bookings = bookingService.getAllBookingDTOs();
        model.addAttribute("bookings", bookings);
        return "bookingHomeCamping-admin/DonDatPhong";//goi den html page
    }

    @GetMapping("/login")
    public String loginPage() {
        return "bookingHomeCamping-user/login";
    }

    // Xử lý đăng nhập (POST)
    @PostMapping("/login")
    public String processLogin() {
        // Logic xử lý đăng nhập
        return "redirect:/home"; // Chuyển hướng sau khi đăng nhập thành công
    }

    // Hiển thị trang đăng ký
    @GetMapping("/register")
    public String showRegisterPage() {
        return "bookingHomeCamping-user/register"; // Trả về template register.html trong thư mục templates/auth
    }

    // Xử lý đăng ký (POST)
    @PostMapping("/register")
    public String processRegister() {
        // Logic xử lý đăng ký
        return "redirect:/login"; // Chuyển hướng đến trang đăng nhập sau khi đăng ký
    }

}

//Layout ...
