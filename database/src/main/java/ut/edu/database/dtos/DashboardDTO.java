package ut.edu.database.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {
    private Long totalBookingsToday;
    private BigDecimal totalRevenueToday;
    private BigDecimal totalRevenueThisMonth;
    private Long totalRoomsBooked;
    private Long totalRoomsAvailable;
    private Long totalUsers;
    private Long totalHomestays;
}
