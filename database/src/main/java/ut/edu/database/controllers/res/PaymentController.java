package ut.edu.database.controllers.res;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ut.edu.database.dtos.PaymentDTO;
import ut.edu.database.services.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public PaymentDTO createPayment(@RequestBody PaymentDTO dto) {
        return paymentService.createPayment(dto);
    }

    @GetMapping
    public List<PaymentDTO> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public PaymentDTO getById(@PathVariable Long id) {
        return paymentService.getPaymentById(id);
    }

    @GetMapping("/booking/{bookingId}")
    public List<PaymentDTO> getByBookingId(@PathVariable Long bookingId) {
        return paymentService.getPaymentsByBookingId(bookingId);
    }
}
