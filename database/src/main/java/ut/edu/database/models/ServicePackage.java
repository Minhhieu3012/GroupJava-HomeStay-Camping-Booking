package ut.edu.database.models;

import jakarta.persistence.*;
import ut.edu.database.enums.TimeSlot;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "service_packages")
public class ServicePackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; //vd: "combo sang - pho+cf"
    private String description;
    private Double price;

    @Enumerated(EnumType.STRING)
    private TimeSlot timeSlot; //sang,chieu,toi

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property; //gan voi homestay/camping

    private Boolean available = true;
}
