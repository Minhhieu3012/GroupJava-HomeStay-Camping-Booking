package ut.edu.database.services;

import ut.edu.database.dtos.PaymentDTO;
import ut.edu.database.models.Payment;
import ut.edu.database.enums.PaymentStatus;
import ut.edu.database.repositories.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    // Chuyển từ Entity -> DTO
    private PaymentDTO toDTO(Payment payment) {
        return new PaymentDTO(
                payment.getId(),
                payment.getBookingId(),
                payment.getTotalAmount(),
                payment.getAdminFee(),
                payment.getHostAmount(),
                payment.getPaymentStatus(),
                payment.getPaymentDate()
        );
    }

    // Chuyển từ DTO -> Entity
    private Payment toEntity(PaymentDTO dto) {
        Payment payment = new Payment();
        payment.setId(dto.getId());
        payment.setBookingId(dto.getBookingId());
        payment.setTotalAmount(dto.getTotalAmount());
        payment.setAdminFee(dto.getAdminFee());
        payment.setHostAmount(dto.getHostAmount());
        payment.setPaymentStatus(dto.getPaymentStatus());
        payment.setPaymentDate(dto.getPaymentDate());
        return payment;
    }

    public PaymentDTO createPayment(PaymentDTO dto) {
        Payment payment = toEntity(dto);
        payment.setPaymentStatus(PaymentStatus.PAID);
        payment.setPaymentDate(LocalDateTime.now());
        return toDTO(paymentRepository.save(payment));
    }

    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PaymentDTO getPaymentById(Long id) {
        Optional<Payment> optional = paymentRepository.findById(id);
        return optional.map(this::toDTO).orElse(null);
    }

    public List<PaymentDTO> getPaymentsByBookingId(Long bookingId) {
        return paymentRepository.findByBookingId(bookingId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
