package admin.bookinghomecamping.controller.auth;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller //MVC
public class AuthController {
    //Define register user name
    @PostMapping("auth/register-admin")
    public String postMethodName(@RequestBody String entity) {

        return entity;
    }
    @GetMapping("auth/register-admin")
    public String RegisterAdmin() {
        return "auth/register";
    }
    @GetMapping("auth/login-admin")
    public String LoginAdmin() {
        return "auth/login";
    }


}
