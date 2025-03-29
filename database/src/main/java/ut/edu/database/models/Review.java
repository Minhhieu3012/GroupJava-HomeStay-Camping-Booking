package ut.edu.database.models;
import jakarta.persistence.*;
import java.time.LocalDate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "Reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL) //xoa review khi user bi xoa
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "propertyID", nullable = false)
    private Property property;

    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private int rating; //1-5

    @NotBlank
    @Column(nullable = false)
    private String comment; //khong cho phep rong

    @Column(nullable = false)
    private LocalDate reviewDate;

    //Constructors
    public Review() {

    }
    public Review(User user, Property property, int rating, String comment, LocalDate reviewDate) {
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

    public int getRating() {
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

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }
}
