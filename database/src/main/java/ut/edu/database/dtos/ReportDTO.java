package ut.edu.database.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import ut.edu.database.enums.ReportStatus;

public class ReportDTO {
    private Long id;
    private Long propertyId;
    private BigDecimal totalRevenue;
    private BigDecimal managementFee;
    private BigDecimal occupancyRate;
    private LocalDate reportDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private ReportStatus status;

    public ReportDTO() {}

    public ReportDTO(Long id, Long propertyId, BigDecimal totalRevenue, BigDecimal managementFee, BigDecimal occupancyRate, LocalDate reportDate, LocalDate startDate, LocalDate endDate, ReportStatus status) {
        this.id = id;
        this.propertyId = propertyId;
        this.totalRevenue = totalRevenue;
        this.managementFee = managementFee;
        this.occupancyRate = occupancyRate;
        this.reportDate = reportDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    //getters setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public BigDecimal getManagementFee() {
        return managementFee;
    }

    public void setManagementFee(BigDecimal managementFee) {
        this.managementFee = managementFee;
    }

    public BigDecimal getOccupancyRate() {
        return occupancyRate;
    }

    public void setOccupancyRate(BigDecimal occupancyRate) {
        this.occupancyRate = occupancyRate;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
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

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }
}
