package admin.bookinghomecamping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import admin.bookinghomecamping.models.admin;
import admin.bookinghomecamping.services.adminService;

import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
//REST API
@RequestMapping("/admin")
public class adminController {
    @Autowired
    private adminService adminService;

    /*method REST
    GET:->  lấy dữ liệu
    Get {id}
    POST:-> thêm mới create
    PUT:-> update toàn bộ (/{id})
    PATCH:-> update một phần của object (/{id})
    DELETE:-> delete (/{id})
    */
    @GetMapping("/admins")
    public Optional<admin> getAllAdmins(String adminName) {
        return adminService.findByadminName(adminName);
    }

    @PostMapping("/admin")
    public long addAdmin(@RequestBody admin admin) {
        return adminService.CreateAdmin(admin);
    }

    @PutMapping("/admin/{id}")
    public long saveAdmin(@RequestBody admin admin) {
        return adminService.CreateAdmin(admin);
    }

    @DeleteMapping("/admin/{id}")
    String deleteAdmin(@PathVariable long id) {
        adminService.deleteAdmin(id);
        return "Admin deleted";
    }

    @Controller
    public class baoCaoDoanhThuController {
        @RequestMapping("/admin/bao-cao-doanh-thu")
        public String baoCaoDoanhThu() {
            return "bookingHomeCamping/BaoCaoDoanhThu";
        }
    }
    @Controller
    public class PhiDichVuController{
        @RequestMapping("/admin/quan-li-phi-dich-vu")
        public String phiDichVu() {
            return "bookingHomeCamping/QuanLiPhiDichVu";
        }
    }
    @Controller
    public class TKChuHomestayController{
        @RequestMapping("/admin/quan-li-tai-khoan-chu-homestay")
        public String taiKhoanChuHomestay() {
            return "bookingHomeCamping/QuanLiTKChuHomestay";
        }
    }
    @Controller
    public class TKNguoiDungController{
        @RequestMapping("/admin/quan-li-tai-khoan-nguoi-dung")
        public String taiKhoanNguoiDung() {
            return "bookingHomeCamping/QuanLiTKNguoiDung";}
        }
    }


