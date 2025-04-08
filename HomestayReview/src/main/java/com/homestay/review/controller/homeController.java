package com.homestay.review.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class homeController {
   @GetMapping("/home")
    public String HomePage() {
       return "home";
    }

}
