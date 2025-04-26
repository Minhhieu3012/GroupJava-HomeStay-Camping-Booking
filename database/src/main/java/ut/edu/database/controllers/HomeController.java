//Thường để xử lí route gốc hoặc tài nguyên public (/api/home, /api)
package ut.edu.database.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ut.edu.database.dtos.MonthlyRevenueDTO;
import ut.edu.database.dtos.PropertyDTO;
import ut.edu.database.services.PropertyService;
import ut.edu.database.services.ReportService;
import ut.edu.database.dtos.ReportDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomeController {
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

    public String baocaodoanhthuPage(Model model) {
        List<ReportDTO> monthlyReports = ReportService.getAllReports(); // Lấy danh sách báo cáo
        List<MonthlyRevenueDTO> monthlyRevenues = ReportService.getMonthlyRevenue(2025, null, true);
        BigDecimal totalRevenue = monthlyReports.stream()
                .map(ReportDTO::getTotalRevenue)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Tính tổng phí quản lý
        BigDecimal managementFee = monthlyReports.stream()
                .map(ReportDTO::getManagementFee)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Tính tỷ lệ lấp đầy trung bình
        BigDecimal occupancyRate = BigDecimal.valueOf(
                monthlyReports.stream()
                        .map(ReportDTO::getOccupancyRate)
                        .filter(Objects::nonNull)
                        .mapToDouble(BigDecimal::doubleValue)
                        .average()
                        .orElse(0.0)
        );

        model.addAttribute("monthlyReports", monthlyReports); // Gửi sang Thymeleaf
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("managementFee", managementFee);
        model.addAttribute("occupancyRate", occupancyRate);
        model.addAttribute("monthlyRevenues", monthlyRevenues);

        return "bookingHomeCamping-admin/BaoCaoDoanhThu";//goi den html page
    }

    @GetMapping("/quan-li-phi-dich-vu")
    public String quanliphidichvuPage() {
        return "bookingHomeCamping-admin/QuanLiPhiDichVu";//goi den html page
    }

    @GetMapping("/quan-li-tk-host")
    public String quanlitkchuhomestayPage() {
        return "bookingHomeCamping-admin/QuanLiTKChuHomestay";//goi den html page
    }

    @GetMapping("/quan-li-tk-user")
    public String quanlitknguoidungPage() {
        return "bookingHomeCamping-admin/QuanLiTKNguoiDung";//goi den html page
    }

    @GetMapping("/them-phong")
    public String themphongPage() {
        return "bookingHomeCamping-admin/ThemPhong";//goi den html page
    }


    private final PropertyService propertyService; // inject service vào
    @GetMapping("/xem-phong")
    public String xemphongPage(Model model) {
        List<PropertyDTO> roomList = propertyService.getAllPropertyDTOs();
        model.addAttribute("roomList", roomList);
        return "bookingHomeCamping-admin/XemPhong";//goi den html page
    }
    @GetMapping("/chinh-sua-phong/{id}")
    public String chinhsuaphongPage(@PathVariable Long id, Model model) {
//        Optional<PropertyDTO> property = propertyService.getPropertyDTOById(id);
//        model.addAttribute("property", property);
//        return "bookingHomeCamping-admin/ChinhSuaPhong";
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
    @PostMapping("/chinh-sua-phong")
    public String updatePhong(@ModelAttribute("property") PropertyDTO dto,
                              @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        propertyService.updatePropertyWithImage(dto, imageFile);
        return "redirect:/xem-phong";
    }

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
    public String dondatphongPage() {
        return "bookingHomeCamping-admin/DonDatPhong";//goi den html page
    }

}

//Layout ...
