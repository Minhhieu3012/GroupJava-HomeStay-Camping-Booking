package com.homestay.review.controller;


import com.homestay.review.model.Review;
import com.homestay.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/add")
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        // Logic thêm đánh giá
        Review savedReview = reviewService.addReview(review);
        return ResponseEntity.ok(savedReview);
    }

    @PostMapping("/respond/{reviewId}")
    public ResponseEntity<Review> respondToReview(@PathVariable Long reviewId, @RequestParam String response) {
        // Logic phản hồi đánh giá
        Review updatedReview = reviewService.respondToReview(reviewId, response);
        return ResponseEntity.ok(updatedReview);
    }
}