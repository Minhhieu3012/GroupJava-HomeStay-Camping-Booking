package propertyregistration.models;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    private double totalAmount;  // Tổng tiền khách thanh toán
    private double adminFee;     // Phí quản lý 20%
    private double hostAmount;   // Số tiền chủ homestay nhận được
    private String paymentStatus; // Trạng thái thanh toán: PENDING, COMPLETED, FAILED

    private LocalDateTime paymentDate;
}

