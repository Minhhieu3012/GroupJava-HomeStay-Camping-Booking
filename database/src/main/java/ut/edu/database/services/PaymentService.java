package ut.edu.database.services;

import lombok.RequiredArgsConstructor;
import ut.edu.database.dtos.MonthlyRevenueDTO;
import ut.edu.database.dtos.PaymentDTO;
import ut.edu.database.models.Booking;
import ut.edu.database.models.Payment;
import ut.edu.database.enums.PaymentStatus;
import ut.edu.database.repositories.BookingRepository;
import ut.edu.database.repositories.PaymentRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    // Entity -> DTO
    private PaymentDTO toDTO(Payment payment) {
        Booking booking = payment.getBooking();

        return new PaymentDTO(
                payment.getId(),
                payment.getBooking().getId(),
                payment.getTotalAmount(),
                payment.getAdminFee(),
                payment.getHostAmount(),
                payment.getPaymentStatus(),
                payment.getPaymentDate(),
                payment.getPaymentMethod(),
                booking.getUser().getId(),
                booking.getUser().getUsername(),
                booking.getProperty().getName()
        );
    }

    // DTO → ENTITY
    private Payment toEntity(PaymentDTO dto) {
        Booking booking = bookingRepository.findById(dto.getBookingId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Booking với id: " + dto.getBookingId()));

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setTotalAmount(booking.getTotalPrice()); // Gốc từ booking
        payment.setAdminFee(booking.getAdminFee());
        payment.setHostAmount(booking.getOwnerEarning());
        payment.setPaymentMethod(dto.getPaymentMethod()); // Lấy từ fe
        payment.setPaymentStatus(PaymentStatus.PAID);
        payment.setPaymentDate(LocalDateTime.now());

        return payment;
    }

    //tao payment moi
    public PaymentDTO createPayment(PaymentDTO dto) {
        Payment payment = toEntity(dto);
        payment.setPaymentStatus(PaymentStatus.PAID);
        payment.setPaymentDate(LocalDateTime.now());
        return toDTO(paymentRepository.save(payment));
    }

    //xem tat ca payment (Admin)
    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    //lay theo id
    public PaymentDTO getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(()->new RuntimeException("Không tìm thây Payment với id: " + id + ":(("));
    }


    //lay theo booking id (OWNER)
    public List<PaymentDTO> getPaymentsByBookingId(Long bookingId) {
        return paymentRepository.findByBookingId(bookingId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<MonthlyRevenueDTO> calculateMonthlyRevenue(int year) {
        List<Payment> payments = paymentRepository.findAll(); // hoặc chỉ tìm những Payment đã SUCCESS

        Map<Integer, BigDecimal> revenueByMonth = new HashMap<>();
        Map<Integer, BigDecimal> adminFeeByMonth = new HashMap<>();

        for (Payment payment : payments) {
            if (payment.getPaymentDate() != null && payment.getPaymentDate().getYear() == year) {
                int month = payment.getPaymentDate().getMonthValue();

                revenueByMonth.put(month,
                        revenueByMonth.getOrDefault(month, BigDecimal.ZERO).add(payment.getTotalAmount()));

                adminFeeByMonth.put(month,
                        adminFeeByMonth.getOrDefault(month, BigDecimal.ZERO).add(payment.getAdminFee()));
            }
        }

        List<MonthlyRevenueDTO> monthlyRevenues = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            BigDecimal totalRevenue = revenueByMonth.getOrDefault(month, BigDecimal.ZERO);
            BigDecimal adminFee = adminFeeByMonth.getOrDefault(month, BigDecimal.ZERO);

            monthlyRevenues.add(new MonthlyRevenueDTO(month, adminFee));
        }

        return monthlyRevenues;
    }

}

