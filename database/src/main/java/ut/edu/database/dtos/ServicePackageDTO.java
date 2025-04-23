package ut.edu.database.dtos;

import lombok.Data;

@Data
public class ServicePackageDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String timeSlot;
    private Long propertyId;
}
