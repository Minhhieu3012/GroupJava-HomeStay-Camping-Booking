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

    // Lấy danh sách đánh giá theo Property ID
    public List<Review> getReviewsByPropertyId(Long propertyId) {
        return reviewRepository.findByPropertyId(propertyId);
    }

    // Lấy danh sách đánh giá theo User ID
    public List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    // Lấy danh sách đánh giá theo số sao (rating)
    public List<Review> getReviewsByRating(Byte rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating phải từ 1 đến 5!");
        }
        return reviewRepository.findByRating(rating);
    }

    // Tạo đánh giá mới
    public Review createReview(Review review) {
        if (review == null || review.getUser() == null || review.getProperty() == null) {
            throw new IllegalArgumentException("Thông tin review không hợp lệ!");
        }
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
