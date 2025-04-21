package com.example.homestay.controller;

import com.example.homestay.entity.Booking;
import com.example.homestay.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping("/create")
    public String createBooking(@RequestBody Booking booking) {
        booking.setStatus("PENDING");
        bookingRepository.save(booking);
        return "Booking created successfully!";
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
