package ut.edu.database.controllers.res;

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
import ut.edu.database.services.BookingService;

import java.util.List;


@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    //GET: lay tat ca dat cho
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookingDTOs());
    }

    //GET: lay dat cho theo id
    //xem booking cu the
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        BookingDTO booking = bookingService.getBookingDTOById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        boolean isOwner = booking.getUserID().equals(user.getUsername());
        boolean isAdmin = user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isOwner && !isAdmin) {
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
        // Lấy booking cũ dưới dạng DTO
        BookingDTO existingDTO = bookingService.getBookingDTOById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // So sánh email để xác thực quyền
        if (!existingDTO.getUserID().equals(bookingService.getUserIdByEmail(user.getUsername()))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Gán ID vào DTO (đảm bảo chính xác)
        updatedDTO.setId(id);
        updatedDTO.setUserID(existingDTO.getUserID());
        updatedDTO.setPropertyID(existingDTO.getPropertyID());

        Booking updatedBooking = bookingMapper.toEntity(updatedDTO);
        Booking saved = bookingService.updateBooking(id, updatedBooking);

        return ResponseEntity.ok(bookingMapper.toDTO(saved));
    }

    // CUSTOMER or ADMIN: Xóa booking
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        BookingDTO booking = bookingService.getBookingDTOById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        boolean isOwner = booking.getUserID().equals(bookingService.getUserIdByEmail(user.getUsername()));
        boolean isAdmin = user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isOwner && !isAdmin) {
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
