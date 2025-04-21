package ut.edu.database.models;
import jakarta.persistence.*;

import ut.edu.database.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID", nullable = false) //nullable dam bao kh co booking nao ton tai ma kh co user
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "propertyID", nullable = false)
    private Property property;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    //luu ds dich vu vao bang rieng
    @ElementCollection
    @CollectionTable(name = "booking_services", joinColumns = @JoinColumn(name = "booking_id", nullable = false))
    @Column(name = "service", nullable = false)
    private List<String> additionalServices = new ArrayList<>();

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;  //day la tong price ma cus phai tra cho toan bo tgian dat cho
                                    //bao gom gia thue co ban va them cac dich vu
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status; //vd: processing, completed, canceled

}
