package ut.edu.database.models;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private BigDecimal price; //BigDecimal de cho gia chinh xac
    private String image; //duong dan hoac url hinh anh
    private String description; //mo ta

    @Enumerated(EnumType.STRING)
    private PropertyStatus status; //vd: available, booked

    //Constructors
    public Property() {

    }
    public Property(String name, String location, BigDecimal price, String image, String description, PropertyStatus status) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.image = image;
        this.description = description;
        this.status = status;
    }

    //Getters Setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public PropertyStatus getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(PropertyStatus status) {
        this.status = status;
    }

    //define enum Property
    public enum PropertyStatus {
        AVAILABLE,
        BOOKED
    }
}
