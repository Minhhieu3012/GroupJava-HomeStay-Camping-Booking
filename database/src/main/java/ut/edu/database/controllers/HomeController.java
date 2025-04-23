package ut.edu.database.controllers;
//Thường để xử lí route gốc hoặc tài nguyên public (/api/home, /api)

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
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

    @GetMapping("/bao-cao-doanh-thu")
    public String baocaodoanhthuPage() {
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
    @GetMapping("/xem-phong")
    public String xemphongPage() {
        return "bookingHomeCamping-admin/XemPhong";//goi den html page
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
