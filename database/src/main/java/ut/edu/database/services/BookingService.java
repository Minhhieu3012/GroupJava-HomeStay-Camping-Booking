package ut.edu.database.services;
import ut.edu.database.models.Booking;
import ut.edu.database.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
}
