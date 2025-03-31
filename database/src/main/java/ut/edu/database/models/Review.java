package ut.edu.database.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;


@Entity
@Table(name = "Reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "propertyID", nullable = false)
    @JsonIgnore
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

    //Constructors
    public Review() {

    }
    public Review(User user, Property property, Byte rating, String comment, LocalDate reviewDate) {
        this.user = user;
        this.property = property;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    //Getters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Property getProperty() {
        return property;
    }

    public Byte getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    //Setters
    public void setUser(User user) {
        this.user = user;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public void setRating(Byte rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }
}
