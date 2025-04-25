package ut.edu.database.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import ut.edu.database.enums.PaymentMethod;
import ut.edu.database.enums.PaymentStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private PaymentMethod paymentMethod;

    private BigDecimal totalAmount;
    private BigDecimal adminFee;
    private BigDecimal hostAmount;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private PaymentStatus paymentStatus;

    private LocalDateTime paymentDate;

}
