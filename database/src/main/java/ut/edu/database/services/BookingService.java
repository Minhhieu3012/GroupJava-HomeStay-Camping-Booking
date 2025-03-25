package ut.edu.database.services;
import ut.edu.database.models.Booking;
import ut.edu.database.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    //
    public List<Booking> getBookingByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }
    //
    public List<Booking> getBookingByPropertyId(Long propertyId) {
        return bookingRepository.findByPropertyId(propertyId);
    }
}
