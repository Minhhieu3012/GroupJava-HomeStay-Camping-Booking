package ut.edu.database.Merge;

import com.example.homestay.entity.Payment;
import com.example.homestay.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public String createPayment(@RequestBody Payment payment) {
        paymentService.createPayment(payment);
        return "Payment recorded successfully!";
    }

    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }
}
