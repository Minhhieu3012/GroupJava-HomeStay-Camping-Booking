package com.homestay.review.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class testController {
    @RequestMapping("/rating")
    public String test() {
        return "/Rating/testimonial";
    }

}