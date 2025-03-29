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

    //lay ds booking theo user id
    public List<Booking> getBookingByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }
    //lay ds booking theo property id
    public List<Booking> getBookingByPropertyId(Long propertyId) {
        return bookingRepository.findByPropertyId(propertyId);
    }
    //lay ds booking theo status
    public List<Booking> getBookingByStatus(String status) {
        try{
            Booking.BookingStatus bookingStatus = Booking.BookingStatus.valueOf(status.toUpperCase()); //chuyen chuoi nhap vaoo thanh chu in hoa, tranh loi nguoi dung nhap chu thuong
            return bookingRepository.findByStatus(bookingStatus.toString()); //chuyen enum thanh chuoi
        }catch(IllegalArgumentException e){ //neu gia tri ko loi se xay ra loi
            return List.of(); //tra ve ds rong
        }
    }
    //tao booking moi
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }
}
