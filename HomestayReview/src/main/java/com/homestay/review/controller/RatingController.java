package com.homestay.review.controller;

import com.homestay.review.model.Rating;
import com.homestay.review.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    // Nhận yêu cầu POST để thêm đánh giá
    @PostMapping
    public Rating submitRating(@RequestBody Rating rating) {
        return ratingService.saveRating(rating);
    }

    // Nhận yêu cầu GET để lấy tất cả đánh giá cho homestay
    @GetMapping("/homestay/{homestayId}")
    public List<Rating> getRatingsForHomestay(@PathVariable Long homestayId) {
        return ratingService.getRatingsForHomestay(homestayId);
    }
}