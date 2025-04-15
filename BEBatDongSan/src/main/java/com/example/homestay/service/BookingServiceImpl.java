package com.example.homestay.service;

import com.example.homestay.entity.Booking;
import com.example.homestay.entity.Homestay;
import com.example.homestay.entity.Service;
import com.example.homestay.entity.User;
import com.example.homestay.entity.Status;
import com.example.homestay.repository.BookingRepository;
import com.example.homestay.repository.HomestayRepository;
import com.example.homestay.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final HomestayRepository homestayRepository;
    private final ServiceRepository serviceRepository;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              HomestayRepository homestayRepository,
                              ServiceRepository serviceRepository) {
        this.bookingRepository = bookingRepository;
        this.homestayRepository = homestayRepository;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public Booking createBooking(Long homestayId, LocalDate startDate, LocalDate endDate, List<Long> serviceIds, User user) {
        Optional<Homestay> homestayOpt = homestayRepository.findById(homestayId);
        if (homestayOpt.isEmpty()) {
            throw new RuntimeException("Không tìm thấy homestay");
        }

        boolean isBooked = bookingRepository.existsByHomestayIdAndDateRangeOverlap(homestayId, startDate, endDate);
        if (isBooked) {
            throw new RuntimeException("Homestay đã được đặt trong khoảng thời gian này");
        }

        Homestay homestay = homestayOpt.get();
        long numberOfDays = startDate.until(endDate).getDays();
        double totalPrice = homestay.getPrice() * numberOfDays;

        List<Service> services = serviceRepository.findAllById(serviceIds);
        for (Service s : services) {
            totalPrice += s.getPrice();
        }

        double adminFee = totalPrice * 0.2;
        double ownerReceive = totalPrice - adminFee;

        Booking booking = new Booking();
        booking.setHomestay(homestay);
        booking.setUser(user);
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);
        booking.setServices(services);
        booking.setTotalPrice(totalPrice);
        booking.setOwnerReceive(ownerReceive);
        booking.setStatus(Status.PENDING);

        return bookingRepository.save(booking);
    }
}
