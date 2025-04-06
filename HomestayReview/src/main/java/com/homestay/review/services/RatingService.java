package com.homestay.review.services;

import com.homestay.review.model.Rating;
import com.homestay.review.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    // Lưu đánh giá
    public Rating saveRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    // Lấy tất cả đánh giá cho homestay cụ thể
    public List<Rating> getRatingsForHomestay(Long homestayId) {
        return ratingRepository.findAll();  // Chưa lọc theo homestayId, bạn có thể bổ sung điều kiện lọc
    }
}
