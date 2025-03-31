package com.homestay.review.service;


import com.homestay.review.model.Review;

public interface ReviewService {
    Review addReview(Long userId, Long homestayId, Review review);
    Review respondToReview(Long reviewId, String response);
}
