package ut.edu.database.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ut.edu.database.models.Review;
import ut.edu.database.repositories.ReviewRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    // Constructor Injection
    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    // Lấy tất cả đánh giá
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // Lấy đánh giá theo ID
    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    // Tạo đánh giá mới
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    // Cập nhật đánh giá
    public Optional<Review> updateReview(Long id, Review updatedReview) {
        return reviewRepository.findById(id).map(existingReview -> {
            existingReview.setRating(updatedReview.getRating());
            existingReview.setComment(updatedReview.getComment());
            existingReview.setReviewDate(updatedReview.getReviewDate());
            return reviewRepository.save(existingReview);
        });
    }

    // Xóa đánh giá theo ID
    public boolean deleteReview(Long id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
