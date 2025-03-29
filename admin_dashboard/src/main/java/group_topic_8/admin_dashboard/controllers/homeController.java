package group_topic_8.admin_dashboard.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class homeController {
    @GetMapping("/home")
    public String HomePage() {
        return "home";
    }
}
