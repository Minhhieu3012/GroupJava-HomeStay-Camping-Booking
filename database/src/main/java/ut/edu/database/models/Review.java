package ut.edu.database.models;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "propertyID", nullable = false)
    private Property property;

    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Byte rating;    //1-5
                            //Dung byte de tiet kiem bo nho

    @NotBlank
    @Column(nullable = false, length = 500)
    private String comment; //khong cho phep rong

    @Column(nullable = false)
    @CreationTimestamp //tu dong lay ngay tao
    private LocalDate reviewDate;

}
