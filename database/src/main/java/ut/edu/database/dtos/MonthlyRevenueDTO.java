package ut.edu.database.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MonthlyRevenueDTO {
    private int month; //thang
    private BigDecimal totalRevenue; //Tong doanh thu thang do (adminFee or ownerEarning)
}
