package ut.edu.database.controllers;
import org.springframework.http.ResponseEntity;
import ut.edu.database.models.Review;
import ut.edu.database.repositories.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ut.edu.database.services.ReviewService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    };

    //lay tat ca danh gia
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    //lay danh gia theo id
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id)
                .map(ResponseEntity::ok) //200 ok
                .orElse(ResponseEntity.notFound().build()); // 404 not found
    }

    //tao danh gia moi
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        try{
            return ResponseEntity.ok(reviewService.createReview(review));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); //404 bad request
        }
    }

    //cap nhat danh gia
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review updatedReview) {
        try{
            return reviewService.updateReview(id,updatedReview)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //xoa danh gia theo id
    @DeleteMapping("/{id}")
    public ResponseEntity<Review> deleteReview(@PathVariable Long id) {
        if(reviewService.deleteReview(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
