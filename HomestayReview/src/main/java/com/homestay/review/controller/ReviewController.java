package com.homestay.review.controller;


import com.homestay.review.model.Review;
import com.homestay.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/{userId}/homestays/{homestayId}")
    public Review addReview(@PathVariable Long userId, @PathVariable Long homestayId, @RequestBody Review review) {
        return reviewService.addReview(userId, homestayId, review);
    }
    @PostMapping("/respond/{reviewId}")
    public Review respondToReview(@PathVariable Long reviewId, @RequestParam String response) {
        return reviewService.respondToReview(reviewId, response);
    }
}
