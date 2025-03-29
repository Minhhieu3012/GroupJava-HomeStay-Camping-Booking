package ut.edu.database.models;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Properties")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 500)
    private String location;

    @Column(nullable = false, precision = 10, scale = 2) //xac dinh 2 chu so thap phan
    private BigDecimal price;   //BigDecimal de cho gia chinh xac cao (tranh lam tron nhu khi dung float or double)
                                //price o day nghia la gia thue co ban, tinh tren don vi tgian, thuong la gia moi dem
    @Column(length = 500)
    private String image; //duong dan hoac url hinh anh

    @Column(columnDefinition = "TEXT")
    private String description; //mo ta

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
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

    //Getters
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

    //Setters
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

    //define enum PropertyStatus
    public enum PropertyStatus {
        AVAILABLE,
        BOOKED
    }
}
