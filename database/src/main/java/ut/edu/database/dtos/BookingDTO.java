package ut.edu.database.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import ut.edu.database.models.Booking;
import ut.edu.database.enums.BookingStatus;
public class BookingDTO {
    private Long id;
    private Long userID;
    private Long propertyID;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> additionalServices;
    private BigDecimal totalPrice;
    private BookingStatus status;

    public BookingDTO() {}

    public BookingDTO(Long id, Long userID, Long propertyID, LocalDate startDate, LocalDate endDate, List<String> additionalServices, BigDecimal totalPrice, BookingStatus status) {
        this.id = id;
        this.userID = userID;
        this.propertyID = propertyID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.additionalServices = additionalServices;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    //getters setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(Long propertyID) {
        this.propertyID = propertyID;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<String> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(List<String> additionalServices) {
        this.additionalServices = additionalServices;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}
