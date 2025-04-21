package ut.edu.database.Merge;

import com.homestay.review.model.Response;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    // Fetch all responses for a specific rating
    List<Response> findByRatingId(Long ratingId);
}
