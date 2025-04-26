package ut.edu.database.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ut.edu.database.enums.PropertyStatus;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDTO {
    private Long id;
    //============Cần tạo=======================
    private String name;
    private String location;
    private BigDecimal price;
    private String image;
    private String[] roomImages;
    private String description;
    private PropertyStatus status;
    private Long owner_id;
    //==================================
}
