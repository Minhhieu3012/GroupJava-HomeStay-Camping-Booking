package com.homestay.review.service;


import com.homestay.review.model.Review;
import com.homestay.review.repository.HomestayRepository;
import com.homestay.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private HomestayRepository homestayRepository;

    public Review addReview(Review review) {
        if (review.getRating() < 1 || review.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        return reviewRepository.save(review);
    }

    public Review respondToReview(Long reviewId, String response) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Review not found"));
        review.setComment(response);
        return reviewRepository.save(review);
    }
}