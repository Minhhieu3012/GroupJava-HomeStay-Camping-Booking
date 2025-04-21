package ut.edu.database.controllers.res;
//Muc tieu:
//Xu li API lien quan den dat cho
//Ap dung phan quyen ROLE_CUSTOMER, ROLE_OWNER, ROLE_ADMIN
//su dung DTO va mapper de bao ve du lieu khi tra ra

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import ut.edu.database.dtos.BookingDTO;
import ut.edu.database.mapper.BookingMapper;
import ut.edu.database.models.Booking;
import ut.edu.database.models.User;
import ut.edu.database.services.BookingService;
import ut.edu.database.services.UserService;

import java.util.List;

@RestController //tra JSON thay vi giao dien
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final BookingMapper bookingMapper;
    private final UserService userService;

    //GET: lay tat ca dat cho
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    //tra ve List<BookingDTO> chu khong phai entity
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookingDTOs());
    }

    //GET: lay dat cho theo id
    //xem booking cu the
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER')  or hasRole('ADMIN')")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        BookingDTO booking = bookingService.getBookingDTOById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        boolean isOwner = booking.getUserID().equals(user.getUsername());
        boolean isCustomer = user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"));
        boolean isAdmin = user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isOwner && !isAdmin && !isCustomer) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(booking);
    }

    //POST: tao dat cho moi
    //CUSTOMER: Tạo mới booking
    @PostMapping("/create")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO dto) {
        Booking booking = bookingService.createBookingDTO(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.bookingToDTO(booking));
    }

    // CUSTOMER: Cập nhật booking của chính mình
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Long id,
                                                    @RequestBody BookingDTO updatedDTO,
                                                    @AuthenticationPrincipal UserDetails user) {
        try {
            // Lấy booking cũ dưới dạng DTO
            BookingDTO existingDTO = bookingService.getBookingDTOById(id)
                    .orElseThrow(() -> new RuntimeException("Booking not found"));

            // Get current user from database
            User currentUser = userService.findByUsername(user.getUsername())
                    .orElseGet(() -> userService.findByEmail(user.getUsername())
                            .orElseThrow(() -> new RuntimeException("User not found")));

            // Compare user IDs for authorization
            if (!existingDTO.getUserID().equals(currentUser.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            // Gán ID vào DTO (đảm bảo chính xác)
            updatedDTO.setId(id);
            updatedDTO.setUserID(currentUser.getId());
            updatedDTO.setPropertyID(existingDTO.getPropertyID());

            Booking updatedBooking = bookingMapper.toEntity(updatedDTO);
            Booking saved = bookingService.updateBooking(id, updatedBooking);

            return ResponseEntity.ok(bookingMapper.toDTO(saved));
        } catch (Exception e) {
            throw e;
        }
    }

    // CUSTOMER or ADMIN: Xóa booking
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN') or hasRole('OWNER')")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        BookingDTO booking = bookingService.getBookingDTOById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        boolean isCustomer = user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"));
        boolean isOwner = booking.getUserID().equals(bookingService.getUserIdByEmail(user.getUsername()));
        boolean isAdmin = user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isOwner && !isAdmin && !isCustomer) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/owner")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<BookingDTO>> getBookingsForOwner(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(bookingService.getBookingsForOwnerProperty(user.getUsername()));
    }

}
