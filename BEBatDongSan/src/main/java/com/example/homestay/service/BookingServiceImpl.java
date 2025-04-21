package com.example.homestay.service.impl;

import com.example.homestay.entity.Booking;
import com.example.homestay.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingExtraServiceRepository extraServiceRepository;

    @Override
    public Booking createBooking(Booking booking, List<String> extraServices) {
        // Lưu booking trước
        Booking savedBooking = bookingRepository.save(booking);

        // Sau đó lưu các dịch vụ bổ sung (nếu có)
        if (extraServices != null) {
            for (String service : extraServices) {
                BookingExtraService extra = new BookingExtraService();
                extra.setBooking(savedBooking);
                extra.setService(service);
                extraServiceRepository.save(extra);
            }
        }

        return savedBooking;
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Booking not found with id: " + id));
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public List<BookingExtraService> getExtrasByBookingId(Long bookingId) {
        return extraServiceRepository.findByBookingId(bookingId);
    }
}
