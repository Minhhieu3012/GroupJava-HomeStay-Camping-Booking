package ut.edu.database.controllers;
import ut.edu.database.models.Booking;
import ut.edu.database.services.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    //Constructor injection de de dang bao mat hon
    @Autowired
    private BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    //lay tat ca dat cho
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    //lay dat cho theo id
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id)
                .map(ResponseEntity::ok) //neu tim thay, tra ve 200 ok
                .orElse(ResponseEntity.notFound().build()); //khong tim thay thi tra ve 404 not found
    }

    //tao dat cho moi
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.createBooking(booking));
    }

    //update dat cho
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking updatedBooking) {
        return bookingService.updateBooking(id, updatedBooking)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //delete dat cho
    @DeleteMapping("/{id}")
    public ResponseEntity<Booking> deleteBooking(@PathVariable Long id) {
        if(bookingService.deleteBooking(id)) {
            return ResponseEntity.noContent().build(); //tra ve 204 No content neu xoa thanh cong
        }
        return ResponseEntity.notFound().build(); //tra ve 404 neu khong tim thay
    }
}
