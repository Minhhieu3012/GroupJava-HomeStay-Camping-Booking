package ut.edu.database.controllers.auth;
//muc tieu:
// tra ve giao dien HTML cho login, register
// chi hien thi giao dien (giao dien static)

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ut.edu.database.dtos.RegisterRequest;

@Controller//MVC - tra ve giao dien HTML ko phai JSON
              //dung cho giao dien dang: login.html, register.html,...
              //khong dung voi postman
public class AuthController {
    //Define register user name
    @PostMapping("auth/register-user")
    public String handleFormRegister(@ModelAttribute RegisterRequest form) {
        // Gọi service xử lý
        return "redirect:/auth/login-user"; // Sau khi đăng ký xong
    }

    @GetMapping("auth/register-user")
    public String RegisterUser() {
        return "auth/register";
    }

    @GetMapping("auth/login-user")
    public String LoginUser() {
        return "auth/login";
    }
}

