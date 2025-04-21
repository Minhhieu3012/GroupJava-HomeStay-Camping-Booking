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

    //loc theo propertyId
    //lay tat ca review ung voi 1 homestay cu the
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<ReviewDTO>> getByPropertyId(@PathVariable Long propertyId) {
        return ResponseEntity.ok(reviewService.getReviewsByPropertyId(propertyId));
    }

    //loc theo userId
    //Lấy review của một user cụ thể (vd: admin or FE muốn hiển thị ds review của 1 nguoi dùng nào đó)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewDTO>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(userId));
    }

    //CUSTOMER: tao review moi (sau khi dang nhap)
    @PostMapping("/create")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO dto, @AuthenticationPrincipal UserDetails user) {
        Long userId = userService.getUserIdByUsername(user.getUsername());
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

    // owner xem cac review ve homestay cua minh
    @GetMapping("/owner")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<ReviewDTO>> getReviewsForOwner(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(reviewService.getReviewsForOwnerProperty(user.getUsername()));
    }
}
