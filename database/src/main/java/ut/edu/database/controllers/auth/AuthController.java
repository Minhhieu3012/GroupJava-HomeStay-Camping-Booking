package ut.edu.database.controllers.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


//GET	http://localhost:8080/auth/register-user (Trả về giao diện đăng ký, truy cập bằng trình duyệt)
//GET	http://localhost:8080/auth/login-user	(Trả về giao diện login, truy cập bằng trình duyệt)
//POST	http://localhost:8080/auth/register-user	(Gửi form đăng ký, HTML form submit (Not Postman)

@Controller //MVC
public class AuthController {
    //Define register user name
    @PostMapping("auth/register-user")
    public String postMethodName(@RequestBody String entity) {
        return entity;
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

