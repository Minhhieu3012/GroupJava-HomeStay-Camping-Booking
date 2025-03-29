package propertyregistration.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "homestays")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Homestay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private double price;
    private int roomCount;
    private String amenities;  // Tiện ích
    private String imageUrl;   // Link hình ảnh
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status; // TRONG hoặc DA_DAT

    @OneToMany(mappedBy = "homestay", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings;
}

enum Status {
    TRONG, DA_DAT
}

