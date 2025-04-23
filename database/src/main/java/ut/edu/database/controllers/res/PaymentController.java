package ut.edu.database.controllers.res;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ut.edu.database.dtos.PaymentDTO;
import ut.edu.database.services.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    //tao payment
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('CUSTOMER','OWNER')")
    public PaymentDTO createPayment(@RequestBody PaymentDTO dto) {
        return paymentService.createPayment(dto);
    }

    //lay toan bo payment
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<PaymentDTO> getAllPayments() {
        return paymentService.getAllPayments();
    }

    //lay payment theo id
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER','OWNER')")
    public PaymentDTO getById(@PathVariable Long id) {
        return paymentService.getPaymentById(id);
    }

    //lay payment theo booking
    //ROLE: OWNER (kiểm tra booking thuộc homestay của mình)
    @GetMapping("/booking/{bookingId}")
    @PreAuthorize("hasRole('OWNER')")
    public List<PaymentDTO> getByBookingId(@PathVariable Long bookingId) {
        return paymentService.getPaymentsByBookingId(bookingId);
    }
}
