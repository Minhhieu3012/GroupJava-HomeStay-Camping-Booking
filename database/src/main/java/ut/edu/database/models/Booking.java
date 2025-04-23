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

    //luu ds combo cus chon vao booking
    //tinh gia tong cong va tao bao cao chi tiet
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "booking_service_packages", joinColumns = @JoinColumn(name = "booking_id"), inverseJoinColumns = @JoinColumn(name = "service_package_id"))
    private List<ServicePackage> servicePackages = new ArrayList<>();

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;  //day la tong price ma cus phai tra cho toan bo tgian dat cho
                                    //bao gom gia thue co ban va them cac dich vu
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status; //vd: processing, completed, canceled

}
