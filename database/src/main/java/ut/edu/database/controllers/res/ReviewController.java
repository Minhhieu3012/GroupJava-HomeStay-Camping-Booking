package ut.edu.database.controllers.res;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import ut.edu.database.dtos.ReviewDTO;
import ut.edu.database.services.ReviewService;
import ut.edu.database.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;

    //Bat ki ai cung xem duoc review
    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    //loc theo property
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<ReviewDTO>> getByPropertyId(@PathVariable Long propertyId) {
        return ResponseEntity.ok(reviewService.getReviewsByPropertyId(propertyId));
    }

    //loc theo user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewDTO>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(userId));
    }

    //CUSTOMER: tao review moi (sau khi dang nhap)
    @PostMapping("/create")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO dto, @AuthenticationPrincipal UserDetails user) {
        Long userId = userService.getUserIdByEmail(user.getUsername());
        dto.setUserID(userId);
        ReviewDTO saved = reviewService.createReview(dto);
        return ResponseEntity.ok(saved);
    }

    //ADMIN xoa review
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/owner")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<ReviewDTO>> getReviewsForOwner(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(reviewService.getReviewsForOwnerProperty(user.getUsername()));
    }

}
