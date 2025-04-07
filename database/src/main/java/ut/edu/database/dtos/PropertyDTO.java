package ut.edu.database.dtos;

import ut.edu.database.models.Property.PropertyStatus;
import java.math.BigDecimal;
public class PropertyDTO {
    private Long id;
    private String name;
    private String location;
    private BigDecimal price;
    private String image;
    private String description;
    private PropertyStatus status;
    private Long ownerId;

    public PropertyDTO() {}

    public PropertyDTO(Long id, String name, String location, BigDecimal price, String image, String description, PropertyStatus status, Long ownerId) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.price = price;
        this.image = image;
        this.description = description;
        this.status = status;
        this.ownerId = ownerId;
    }

    //getters setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PropertyStatus getStatus() {
        return status;
    }

    public void setStatus(PropertyStatus status) {
        this.status = status;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
