package com.example.homestay.controller;

import com.example.homestay.entity.Booking;
import com.example.homestay.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        try {
            Booking savedBooking = bookingService.createBooking(booking);
            return ResponseEntity.ok(savedBooking);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/availability")
    public ResponseEntity<?> checkAvailability(
            @RequestParam Long homestayId,
            @RequestParam String checkInDate,
            @RequestParam String checkOutDate) {
        boolean available = bookingService.checkAvailability(
                homestayId,
                LocalDate.parse(checkInDate),
                LocalDate.parse(checkOutDate)
        );
        return ResponseEntity.ok(available);
    }
}