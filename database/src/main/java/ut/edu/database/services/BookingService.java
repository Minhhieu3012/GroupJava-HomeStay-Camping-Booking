package ut.edu.database.services;

import lombok.RequiredArgsConstructor;

import ut.edu.database.dtos.BookingDTO;
import ut.edu.database.enums.BookingStatus;
import ut.edu.database.mapper.BookingMapper;
import ut.edu.database.models.Booking;
import ut.edu.database.models.Property;
import ut.edu.database.models.ServicePackage;
import ut.edu.database.models.User;
import ut.edu.database.repositories.BookingRepository;

import org.springframework.stereotype.Service;
import ut.edu.database.repositories.ServicePackageRepository;

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
    private final ServicePackageRepository servicePackageRepository;

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

    // Update this method to handle both email and username
    public Long getUserIdByEmail(String usernameOrEmail) {
        // First try by username
        Optional<User> userByUsername = userService.findByUsername(usernameOrEmail);
        if (userByUsername.isPresent()) {
            return userByUsername.get().getId();
        }
        
        // If not found by username, try by email
        return userService.findByEmail(usernameOrEmail)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy User với tên hoặc email là: " + usernameOrEmail))
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
            throw new RuntimeException("Trạng thái không hợp lệ :((: " +status);
        }
    }

    // Tạo booking mới
    public Booking createBookingDTO(BookingDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("BookingDTO không có hiệu lực :((");
        }
        // Kiểm tra overlap (trùng lịch)
        List<Booking> overlaps = bookingRepository.findOverlappingBookings(
                dto.getPropertyID(), dto.getStartDate(), dto.getEndDate()
        );

        if (!overlaps.isEmpty()) {
            throw new RuntimeException("Phòng đã được đặt trong khoảng thời gian này, vui lòng chọn ngày khác!");
        }
        if (dto.getStartDate().isAfter(dto.getEndDate())) {
            throw new IllegalArgumentException("Ngày bắt đầu phải trước ngày kết thúc -.-");
        }
        Booking booking = bookingMapper.toEntity(dto);

        User user = userService.getUserById(dto.getUserID())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy User với id: " + dto.getUserID()));
        Property property = propertyService.getPropertyById(dto.getPropertyID())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Property với id: " + dto.getPropertyID()));

        booking.setUser(user);
        booking.setProperty(property);

        //Gan cac ServicePackage duoc chon
        if(dto.getServicePackageIds() != null & !dto.getServicePackageIds().isEmpty()) {
            List<ServicePackage> packages = dto.getServicePackageIds().stream()
                    .map(id->servicePackageRepository.findById(id)
                    .orElseThrow(()->new RuntimeException("Không tìm thấy ServicePackage: "+id)))
                    .toList();
            booking.setServicePackages(packages);
        }

        //Tinh toan totalPrice = basePrice + combo
        long numberOfNights = java.time.temporal.ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate());
        BigDecimal basePrice = property.getPrice().multiply(BigDecimal.valueOf(numberOfNights));
        BigDecimal serviceTotal = booking.getServicePackages().stream()
                .map(sp->BigDecimal.valueOf(sp.getPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPrice = basePrice.add(serviceTotal);
        booking.setTotalPrice(totalPrice);

        //tinh phi admin 20%, owner nhan 80%
        BigDecimal adminFee = totalPrice.multiply(BigDecimal.valueOf(0.2));
        BigDecimal ownerEarning = totalPrice.subtract(adminFee);
        booking.setAdminFee(adminFee);
        booking.setOwnerEarning(ownerEarning);

        booking.setStatus(BookingStatus.PROCESSING); //default status

        return bookingRepository.save(booking);
    }

    //update booking theo id
    public Booking updateBooking(Long id, Booking updatedBooking) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Booking với id: " + id));
            // Cập nhật tất cả các thông tin có thể sửa đổi
            existingBooking.setStatus(updatedBooking.getStatus());
            existingBooking.setStartDate(updatedBooking.getStartDate());
            existingBooking.setEndDate(updatedBooking.getEndDate());
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

    public List<BookingDTO> getBookingsForOwnerProperty(String usernameOrEmail) {
        Long ownerId = userService.getUserIdByUsername(usernameOrEmail); //dung ham co san
        List<Booking> bookings = bookingRepository.findAll().stream()
                .filter(booking -> booking.getProperty() != null
                        && booking.getProperty().getOwner() != null
                        && booking.getProperty().getOwner().getId().equals(ownerId))
                .toList();

        return bookings.stream()
                .map(bookingMapper::toDTO)
                .toList();
    }

    public boolean isOwnerOfBooking(Long propertyId, String usernameOrEmail) {
        Long ownerId = userService.getUserIdByUsername(usernameOrEmail);
        return propertyService.getPropertyById(propertyId)
                .map(property -> property.getOwner().getId().equals(ownerId))
                .orElse(false);
    }

}