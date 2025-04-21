package com.example.homestay.service;

import com.example.homestay.entity.Booking;

import java.util.List;

public interface BookingService {
    Booking createBooking(Booking booking, List<String> extraServices);
    List<Booking> getAllBookings();
    Booking getBookingById(Long id);
    void deleteBooking(Long id);
}
