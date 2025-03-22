package ut.edu.database.models;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "propertyID")
    private Property property;

    private LocalDate startDate;
    private LocalDate endDate;
    private String additionalServices; //luu duoi dang chuoi (vd: "breakfast,dinner")
    private BigDecimal totalPrice;  //day la tong price ma cus phai tra cho toan bo tgian dat cho
                                    //bao gom gia thue co ban va them cac dich vu

    @Enumerated(EnumType.STRING)
    private BookingStatus status; //vd: processing, completed, canceled

    //Constructors
    public Booking() {

    }
    public Booking(User user, Property property, LocalDate startDate, LocalDate endDate, String additionalServices, BigDecimal totalPrice, BookingStatus status) {
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

    public String getAdditionalServices() {
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

    public void setAdditionalServices(String additionalServices) {
        this.additionalServices = additionalServices;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    //define enum BookingStatus
    public enum BookingStatus {
        PROCESSING,
        COMPLETED,
        CANCELLED
    }
}
