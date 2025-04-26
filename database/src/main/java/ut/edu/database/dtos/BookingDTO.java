package ut.edu.database.dtos;

import ut.edu.database.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    //=============Cần tạo=======================
    private Long userID;
    private Long propertyID;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> additionalServices;
    private List<Long> servicePackageIds;
    //=====================================
    private Long id;
    private BigDecimal totalPrice;
    private BookingStatus status;
    private BigDecimal adminFee;
    private BigDecimal ownerEarning;

}
