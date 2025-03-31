package ut.edu.database.models;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Liên kết với Property (Many-to-One)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal totalRevenue; //tong doanh thu

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal managementFee; //phi quan ly

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal occupancyRate; //ti le lap day (%)

    @Column(nullable = false)
    private LocalDate reportDate;


    @Column(nullable = false)
    private LocalDate startDate; // Thêm trường startDate

    @Column(nullable = false)
    private LocalDate endDate;   // Thêm trường endDate

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status;

    //Constructors
    public Report() {

    }
    public Report(Property property ,BigDecimal totalRevenue, BigDecimal managementFee, BigDecimal occupancyRate, LocalDate reportDate, ReportStatus status, LocalDate startDate, LocalDate endDate) {
        this.property = property;
        this.totalRevenue = totalRevenue; //tong doanh thu
        this.managementFee = managementFee; //phi quan ly
        this.occupancyRate = occupancyRate; //ti le lap day
        this.reportDate = reportDate; //ngay bao cao
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //Getters
    public Property getProperty() {
        return property;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public BigDecimal getManagementFee() {
        return managementFee;
    }

    public BigDecimal getOccupancyRate() {
        return occupancyRate;
    }

    public ReportStatus getStatus() {
        return status;
    }

    //Setters
    public void setProperty(Property property) {
        this.property = property;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public void setManagementFee(BigDecimal managementFee) {
        this.managementFee = managementFee;
    }

    public void setOccupancyRate(BigDecimal occupancyRate) {
        this.occupancyRate = occupancyRate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    //define enum ReportStatus
    public enum ReportStatus {
        GENERATED, //report duoc tao
        APPROVED //report duoc phe duyet
    }
}
