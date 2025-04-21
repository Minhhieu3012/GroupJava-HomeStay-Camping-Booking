package ut.edu.database.services;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import ut.edu.database.dtos.ReviewDTO;
import ut.edu.database.mapper.ReviewMapper;
import ut.edu.database.models.Property;
import ut.edu.database.models.Review;
import ut.edu.database.models.User;
import ut.edu.database.repositories.ReviewRepository;
import ut.edu.database.repositories.UserRepository;
import ut.edu.database.repositories.PropertyRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewMapper reviewMapper;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final UserService userService;


    // Lấy tất cả đánh giá
    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Lấy danh sách đánh giá theo Property ID
    public List<ReviewDTO> getReviewsByPropertyId(Long propertyId) {
        return reviewRepository.findByPropertyId(propertyId)
                .stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Lấy danh sách đánh giá theo User ID
    public List<ReviewDTO> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId)
                .stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Lấy danh sách đánh giá theo số sao (rating)
    public List<ReviewDTO> getReviewsByRating(Byte rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating phải từ 1 đến 5!");
        }
        return reviewRepository.findByRating(rating)
                .stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Tạo đánh giá mới
    public ReviewDTO createReview(ReviewDTO dto) {
        User user = userRepository.findById(dto.getUserID())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Property property = propertyRepository.findById(dto.getPropertyID())
                .orElseThrow(() -> new IllegalArgumentException("Property not found"));


        //set lai thong tin de dam bao dung
        Review review = reviewMapper.toEntity(dto);
        review.setId(null); //Bắt buộc xóa ID nếu có => tránh Hibernate hiểu nhầm là UPDATE
        review.setUser(user);
        review.setProperty(property);
        review.setRating(dto.getRating());
        review.setReviewDate(LocalDate.now());

        Review saved = reviewRepository.save(review);
        return reviewMapper.toDTO(saved);
    }

    // Xóa đánh giá theo ID
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    public Optional<ReviewDTO> getReviewById(Long id) {
        return reviewRepository.findById(id).map(reviewMapper::toDTO);
    }

    public List<ReviewDTO> getReviewsForOwnerProperty(String usernameOrEmail) {
        Long ownerId = userService.getUserIdByUsername(usernameOrEmail);
        List<Review> reviews = reviewRepository.findAll().stream()
                .filter(review -> review.getProperty() != null
                        && review.getProperty().getOwner() != null
                        && review.getProperty().getOwner().getId().equals(ownerId))
                .toList();

        return reviews.stream()
                .map(reviewMapper::toDTO)
                .toList();
    }
}

