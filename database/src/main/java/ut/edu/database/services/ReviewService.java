package ut.edu.database.services;
import ut.edu.database.models.Review;
import ut.edu.database.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    //
    List<Review> getReviewsByPropertyId(Long propertyId) {
        return reviewRepository.findByPropertyId(propertyId);
    }
    //
    List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }
    //
    List<Review> getReviewsByRating(int rating) {
        return reviewRepository.findByRating(rating);
    }
    //
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }
}
