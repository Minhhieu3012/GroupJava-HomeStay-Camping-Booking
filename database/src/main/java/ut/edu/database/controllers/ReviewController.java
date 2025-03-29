package ut.edu.database.controllers;
import ut.edu.database.models.Review;
import ut.edu.database.repositories.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewRepository reviewRepository;

    //lay tat ca danh gia
    @GetMapping
    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }
    //lay danh gia theo id
    @GetMapping("/{id}")
    public Optional<Review> getReviewById(@PathVariable Long id) {
        return reviewRepository.findById(id);
    }
    //tao danh gia moi
    @PostMapping
    public Review createReview(@RequestBody Review review) {
        return reviewRepository.save(review);
    }
    //cap nhat danh gia
    @PutMapping("/{id}")
    public Review updateReview(@PathVariable Long id, @RequestBody Review review) {
        return reviewRepository.save(review);
    }
    //xoa danh gia
    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewRepository.deleteById(id);
    }
}
