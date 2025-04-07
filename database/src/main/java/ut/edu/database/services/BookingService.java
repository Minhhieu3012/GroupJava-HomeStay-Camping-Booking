package ut.edu.database.services;
import org.springframework.beans.factory.annotation.Autowired;
import ut.edu.database.enums.BookingStatus;
import ut.edu.database.models.Booking;
import ut.edu.database.repositories.BookingRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    //Constructor Injection
    private  final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    //lay ds tat ca cac booking
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Lấy booking theo ID
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
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
        try {
            BookingStatus bookingStatus = BookingStatus.valueOf(status.toUpperCase());
            return bookingRepository.findByStatus(bookingStatus); // truyen enum truc tiep
        } catch(IllegalArgumentException e) {
            throw new RuntimeException("Invalid booking status: " + status);
        }
    }

    // Lấy danh sách dịch vụ bổ sung của một booking
    public List<String> getAdditionalServices(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .map(Booking::getAdditionalServices)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));
    }

    // Lấy tổng giá booking
    public BigDecimal getTotalPrice(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .map(Booking::getTotalPrice)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));
    }

    // Tạo booking mới
    public Booking createBooking(Booking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("Invalid booking data");
        }
        return bookingRepository.save(booking);
    }

    //update booking theo id
    public Optional<Booking> updateBooking(Long id, Booking updatedBooking) {
        return bookingRepository.findById(id).map(existingBooking -> {
            // Cập nhật tất cả các thông tin có thể sửa đổi
            existingBooking.setStatus(updatedBooking.getStatus());
            existingBooking.setStartDate(updatedBooking.getStartDate());
            existingBooking.setEndDate(updatedBooking.getEndDate());
            existingBooking.setAdditionalServices(updatedBooking.getAdditionalServices());
            existingBooking.setTotalPrice(updatedBooking.getTotalPrice());
            return bookingRepository.save(existingBooking);
        });
    }

    // Xoa booking theo id
    public boolean deleteBooking(Long id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return true;
        }
        return false; // khong tim thay booking, tra ve false
    }
}
