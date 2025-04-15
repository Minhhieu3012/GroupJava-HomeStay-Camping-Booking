//package com.homestay.review.controller;
//
//
//import com.homestay.review.model.Response;
//import com.homestay.review.service.ResponseService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/responses")
//public class ResponseController {
//
//    @Autowired
//    private ResponseService responseService;
//
//    // Nhận yêu cầu POST để thêm phản hồi
//    @PostMapping
//    public Response submitResponse(@RequestBody Response response) {
//        return responseService.saveResponse(response);
//    }
//}