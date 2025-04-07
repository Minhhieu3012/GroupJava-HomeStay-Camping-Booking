package ut.edu.database.models;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import ut.edu.database.enums.BookingStatus;

@Entity
@Table(name = "Booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userID", nullable = false) //nullable dam bao kh co booking nao ton tai ma kh co user
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "propertyID", nullable = false)
    private Property property;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    //luu ds dich vu vao bang rieng
    @ElementCollection
    @CollectionTable(name = "booking_services", joinColumns = @JoinColumn(name = "booking_id"))
    @Column(name = "service")
    private List<String> additionalServices = new ArrayList<>();

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;  //day la tong price ma cus phai tra cho toan bo tgian dat cho
                                    //bao gom gia thue co ban va them cac dich vu
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status; //vd: processing, completed, canceled

    //Constructors
    public Booking() {

    }
    public Booking(User user, Property property, LocalDate startDate, LocalDate endDate, List<String> additionalServices, BigDecimal totalPrice, BookingStatus status) {
        this.user = user;
        this.property = property;
        this.startDate = startDate;
        this.endDate = endDate;
        this.additionalServices = additionalServices;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    //Getters

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Property getProperty() {
        return property;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public List<String> getAdditionalServices() {
        return additionalServices;
    }

    //Setters
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setAdditionalServices(List<String> additionalServices) {
        this.additionalServices = additionalServices;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

}
