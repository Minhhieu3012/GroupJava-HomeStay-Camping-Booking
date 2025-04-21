package ut.edu.database.Merge;

import com.example.homestay.entity.Payment;

import java.util.List;

public interface PaymentService {
    Payment createPayment(Payment payment);
    List<Payment> getAllPayments();

    Payment getPaymentById(Long id);
    List<Payment> getPaymentsByBookingId(Long bookingId);
}
