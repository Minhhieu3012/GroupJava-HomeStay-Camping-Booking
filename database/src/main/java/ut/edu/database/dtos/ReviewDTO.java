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
    private Long userId;
    private Long propertyId;
    private Byte rating;
    private String comment;
    private LocalDate reviewDate;

}
