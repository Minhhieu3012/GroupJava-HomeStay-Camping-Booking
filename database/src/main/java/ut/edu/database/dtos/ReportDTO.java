package ut.edu.database.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import ut.edu.database.enums.ReportStatus;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
    private Long id;
    //===============Cần tạo===========================
    private Long propertyID;
    private BigDecimal totalRevenue;
    private BigDecimal managementFee;
    private BigDecimal occupancyRate;
    private LocalDate reportDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private ReportStatus status;
    //=================================================

    //lấy số tháng từ reportDate
    public String getMonth() {
        return reportDate != null ? String.valueOf(reportDate.getMonthValue()) : "";
    }
}
