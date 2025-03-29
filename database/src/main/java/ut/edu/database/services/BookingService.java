package ut.edu.database.services;
import ut.edu.database.models.Booking;
import ut.edu.database.repositories.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    //Constructor Injection
    private  final BookingRepository bookingRepository;
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    //lay ds booking theo user id
    public List<Booking> getBookingByUserId(Long userId) {
        if(userId == null || userId <= 0){
            throw new IllegalArgumentException("Invalid user id");
        }
        return bookingRepository.findByUserId(userId);
    }
    //lay ds booking theo property id
    public List<Booking> getBookingByPropertyId(Long propertyId) {
        if(propertyId == null || propertyId <= 0){
            throw new IllegalArgumentException("Invalid property id");
        }
        return bookingRepository.findByPropertyId(propertyId);
    }
    //lay ds booking theo status
    public List<Booking> getBookingByStatus(String status) {
        try{
            Booking.BookingStatus bookingStatus = Booking.BookingStatus.valueOf(status.toUpperCase()); //chuyen chuoi nhap vaoo thanh chu in hoa, tranh loi nguoi dung nhap chu thuong
            return bookingRepository.findByStatus(bookingStatus.toString()); //chuyen enum thanh chuoi
        }catch(IllegalArgumentException e){ //neu gia tri ko loi se xay ra loi
            throw new RuntimeException("Invalid booking status: "+status); //bat loi va nem ngoai le khac kem thong bao
        }
    }
    //tao booking moi
    public Booking createBooking(Booking booking) {
       if(booking == null){
           throw new IllegalArgumentException("Invalid booking");
       }
       return bookingRepository.save(booking);
    }
}
