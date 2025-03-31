package com.homestay.review.service;


import com.homestay.review.model.Review;
import com.homestay.review.repository.ReviewRepository;
import com.homestay.review.repository.UserRepository;
import com.homestay.review.repository.HomestayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HomestayRepository homestayRepository;

    @Override
    public Review addReview(Long userId, Long homestayId, Review review) {
        // Kiểm tra nếu người dùng đã đặt homestay trước khi đánh giá
        if (true) { // Thêm logic kiểm tra nếu người dùng đã đặt
            review.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
            review.setHomestay(homestayRepository.findById(homestayId).orElseThrow(() -> new RuntimeException("Homestay not found")));
            return reviewRepository.save(review);
        }
        throw new RuntimeException("User must complete a booking before leaving a review.");
    }

    @Override
    public Review respondToReview(Long reviewId, String response) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Review not found"));
        review.setComment(response);
        return reviewRepository.save(review);
    }
}
