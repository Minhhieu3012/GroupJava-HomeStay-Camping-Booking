package ut.edu.database.Merge;

import com.example.homestay.entity.Payment;
import com.example.homestay.repository.PaymentRepository;
import com.example.homestay.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment createPayment(Payment payment) {
        payment.setStatus("PAID");
        payment.setPaymentTime(LocalDateTime.now());
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment getPaymentById(Long id) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);
        return optionalPayment.orElse(null);
    }

    @Override
    public List<Payment> getPaymentsByBookingId(Long bookingId) {
        return paymentRepository.findByBookingId(bookingId);
    }
}
