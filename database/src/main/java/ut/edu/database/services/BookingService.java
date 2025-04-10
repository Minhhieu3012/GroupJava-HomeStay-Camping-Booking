package ut.edu.database.services;

import lombok.RequiredArgsConstructor;

import ut.edu.database.dtos.BookingDTO;
import ut.edu.database.enums.BookingStatus;
import ut.edu.database.mapper.BookingMapper;
import ut.edu.database.models.Booking;
import ut.edu.database.models.Property;
import ut.edu.database.models.User;
import ut.edu.database.repositories.BookingRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {
    //Constructor Injection
    private  final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserService userService;
    private final PropertyService propertyService;

    // Trả về danh sách BookingDTO để gửi ra ngoài cho client
    public List<BookingDTO> getAllBookingDTOs() {
        return bookingRepository.findAll()
                .stream()
                .map(bookingMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BookingDTO bookingToDTO(Booking booking) {
        return bookingMapper.toDTO(booking);
    }

    public Long getUserIdByEmail(String email) {
        return userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email))
                .getId();
    }

    // Lấy booking theo ID
    public Optional<BookingDTO> getBookingDTOById(Long id) {
        return bookingRepository.findById(id)
                .map(bookingMapper::toDTO);
    }

    //lay ds theo status
    public List<BookingDTO> filterByStatus(String status) {
        try {
            BookingStatus enumStatus = BookingStatus.valueOf(status.toUpperCase());
            return bookingRepository.findByStatus(enumStatus).stream() // truyen enum truc tiep
                    .map(bookingMapper::toDTO)
                    .collect(Collectors.toList());
        } catch(IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " +status);
        }
    }

    // Lấy ds dịch vụ bổ sung
    public BookingDTO getAdditionalServices(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .map(bookingMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));
    }

    // Lấy tổng giá booking
    public BigDecimal getTotalPrice(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .map(Booking::getTotalPrice)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));
    }

    // Tạo booking mới
    public Booking createBookingDTO(BookingDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("BookingDTO is null");
        }
        if (dto.getStartDate().isAfter(dto.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        Booking booking = bookingMapper.toEntity(dto);
        User user = userService.getUserById(dto.getUserID())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserID()));
        Property property = propertyService.getPropertyById(dto.getPropertyID())
                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + dto.getPropertyID()));
        booking.setUser(user);
        booking.setProperty(property);

        return bookingRepository.save(booking);
    }

    //update booking theo id
    public Booking updateBooking(Long id, Booking updatedBooking) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
            // Cập nhật tất cả các thông tin có thể sửa đổi
            existingBooking.setStatus(updatedBooking.getStatus());
            existingBooking.setStartDate(updatedBooking.getStartDate());
            existingBooking.setEndDate(updatedBooking.getEndDate());
            existingBooking.setAdditionalServices(updatedBooking.getAdditionalServices());
            existingBooking.setTotalPrice(updatedBooking.getTotalPrice());

            return bookingRepository.save(existingBooking);
    }

    // Xoa booking theo id
    public boolean deleteBooking(Long id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return true;
        }
        return false; // khong tim thay booking, tra ve false
    }

    //ham ho tro kiem tra ID
    private void validateId(Long id, String label) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid " + label + " ID");
        }
    }
}
