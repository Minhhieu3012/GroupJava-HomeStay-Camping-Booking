package ut.edu.database.services;
import ut.edu.database.models.Property;
import ut.edu.database.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {
    @Autowired
    private ReviewRepository reviewRepository;
}
