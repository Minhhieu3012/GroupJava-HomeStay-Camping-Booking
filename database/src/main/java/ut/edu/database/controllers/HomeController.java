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
    @GetMapping("/quan-li-tk-host-user")
    public String quanlitkchuhomestay_nguoidungPage() {
        return "bookingHomeCamping-admin/QuanLiTKChuHomestay-NguoiDung";//goi den html page
    }
    @GetMapping("/them-phong")
    public String themphongPage() {
        return "bookingHomeCamping-admin/ThemPhong";//goi den html page
    }
    @GetMapping("/xoa-phong")
    public String xoaphongPage() {
        return "bookingHomeCamping-admin/XoaPhong";//goi den html page
    }

}

//Layout ...
