package ut.edu.database.controllers;

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
}
//Layout ...
