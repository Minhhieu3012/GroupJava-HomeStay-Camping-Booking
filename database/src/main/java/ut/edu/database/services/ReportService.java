package ut.edu.database.services;
import ut.edu.database.models.Report;
import ut.edu.database.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    @Autowired
    private ReviewRepository reviewRepository;
}
