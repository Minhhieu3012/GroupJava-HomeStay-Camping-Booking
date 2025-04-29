package ut.edu.database.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomDTO {
    private Long id;
    private String name;
    private String image;
    private int beds;
    private int baths;
    private int rating;
    private String description;
    private double price;
}
