package ut.edu.database.services;
import org.springframework.beans.factory.annotation.Autowired;
import ut.edu.database.models.Review;
import ut.edu.database.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    //Constructor Injection
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    //lay ds danh gia theo id bat dong san
    List<Review> getReviewsByPropertyId(Long propertyId) {
        if(propertyId == null || propertyId <= 0){
            throw new IllegalArgumentException("Invalid property id");
        }
        return reviewRepository.findByPropertyId(propertyId);
    }

    //lay ds danh gia theo user id
    List<Review> getReviewsByUserId(Long userId) {
        if(userId == null || userId <= 0){
            throw new IllegalArgumentException("Invalid user id");
        }
        return reviewRepository.findByUserId(userId);
    }

    //lay ds danh gia theo rating (1-5 sao)
    List<Review> getReviewsByRating(int rating) {
        if(rating < 1 || rating > 5){
            throw new IllegalArgumentException("Invalid rating");
        }
        return reviewRepository.findByRating(rating);
    }

    //tao danh gia moi
    public Review createReview(Review review) {
        if(review == null){
            throw new IllegalArgumentException("Review cannot be null");
        }
        return reviewRepository.save(review);
    }
}
