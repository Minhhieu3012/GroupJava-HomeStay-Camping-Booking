package ut.edu.database.models;
import ut.edu.database.enums.ReportStatus;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // lien ket voi Property (Many-to-One)
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
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status;

}
