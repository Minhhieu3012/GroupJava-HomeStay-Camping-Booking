package ut.edu.database.models;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal totalRevenue;
    private BigDecimal managementFee;
    private BigDecimal occupancyRate;
    private LocalDate reportDate;

    @Enumerated(EnumType.STRING)
    private ReportStatus status;

    //Constructors
    public Report() {

    }
    public Report(BigDecimal totalRevenue, BigDecimal managementFee, BigDecimal occupancyRate, LocalDate reportDate, ReportStatus status) {
        this.totalRevenue = totalRevenue; //tong doanh thu
        this.managementFee = managementFee; //phi quan ly
        this.occupancyRate = occupancyRate; //ti le lap day
        this.reportDate = reportDate; //ngay bao cao
        this.status = status;
    }

    //Getters
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

    public LocalDate getReportDate() {
        return reportDate;
    }

    public ReportStatus getStatus() {
        return status;
    }

    //Setters
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
