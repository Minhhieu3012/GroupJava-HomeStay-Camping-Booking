package ut.edu.database.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarouselDTO {
    private String image;
    private String subtitle;
    private String title;
    private boolean active;
}
