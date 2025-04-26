package ut.edu.database.dtos;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Long id;
    //===========Cần tạo==================
    private Long userID;
    private Long propertyID;
    private Byte rating;
    private String comment;
    private LocalDate reviewDate;
    //==============================
}
