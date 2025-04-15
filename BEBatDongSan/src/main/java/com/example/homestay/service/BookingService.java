package com.example.homestay.service;

import com.example.homestay.entity.Booking;
import com.example.homestay.entity.Homestay;
import com.example.homestay.repository.BookingRepository;
import com.example.homestay.repository.HomestayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private HomestayRepository homestayRepository;

    public Booking createBooking(Booking booking) {
        Homestay homestay = homestayRepository.findById(booking.getHomestayId())
                .orElseThrow(() -> new IllegalArgumentException("Homestay không tồn tại"));

        if (booking.getCheckInDate().isAfter(booking.getCheckOutDate())) {
            throw new IllegalArgumentException("Ngày nhận phòng phải trước ngày trả phòng");
        }

        List<Booking> conflicts = bookingRepository.findConflictingBookings(
                booking.getHomestayId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate()
        );
        if (!conflicts.isEmpty()) {
            throw new IllegalArgumentException("Homestay đã được đặt trong khoảng thời gian này");
        }

        long nights = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
        booking.setTotalPrice(nights * homestay.getPricePerNight());
        booking.setStatus("PENDING");

        return bookingRepository.save(booking);
    }

    public boolean checkAvailability(Long homestayId, LocalDate checkInDate, LocalDate checkOutDate) {
        List<Booking> conflicts = bookingRepository.findConflictingBookings(homestayId, checkInDate, checkOutDate);
        return conflicts.isEmpty();
    }
}
