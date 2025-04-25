package ut.edu.database.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import ut.edu.database.enums.PaymentMethod;
import ut.edu.database.enums.PaymentStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Long id;
    private Long bookingId;
    private BigDecimal totalAmount;
    private BigDecimal adminFee;
    private BigDecimal hostAmount;
    private PaymentStatus paymentStatus;
    private LocalDateTime paymentDate;
    private PaymentMethod paymentMethod; // VNPAY, MOMO, CREDIT_CARD, CASH
}
