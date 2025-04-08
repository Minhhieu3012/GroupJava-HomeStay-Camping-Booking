package com.homestay.review.service;

import com.homestay.review.model.Rating;
import com.homestay.review.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public Rating saveRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    public List<Rating> getRatingsForHomestay(Long homestayId) {
        return ratingRepository.findAll();  // Add filtering by homestayId later
    }
}