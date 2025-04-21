package admin.bookinghomecamping.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private double price;
    private String image;
    private String description;
    private String status;
    private Long ownerId;

    // getters và setters
}
