package com.homestay.review.service;

import com.homestay.review.model.Rating;
import com.homestay.review.repository.RatingRepository;
import com.homestay.review.dto.RatingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ResponseService responseService;

    public Rating saveRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    public List<RatingDTO> getRatingsForHomestay(Long homestayId) {
        List<Rating> ratings = ratingRepository.findAll(); // Add filter by homestayId later
        return ratings.stream().map(rating -> {
            RatingDTO dto = new RatingDTO();
            dto.setHomestayId(rating.getHomestay().getId());
            dto.setRatingValue(rating.getRatingValue());
            dto.setComment(rating.getComment());
            return dto;
        }).collect(Collectors.toList());
    }
}
