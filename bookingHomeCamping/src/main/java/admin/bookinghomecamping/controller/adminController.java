package admin.bookinghomecamping.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import admin.bookinghomecamping.models.admin;
import admin.bookinghomecamping.services.adminService;

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
}
