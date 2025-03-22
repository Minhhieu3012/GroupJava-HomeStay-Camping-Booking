package ut.edu.database.models;
import jakarta.persistence.*;
import java.time.LocalDate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import java.util.Objects;

@Entity
@Table(name = "Review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "propertyID")
    private Property property;


    @Min(1)
    @Max(5)
    private int rating; //1-5
    private String comment;
    private LocalDate reviewDate;

    //Constructors
    public Review() {

    }
    public Review(User user, Property property, int rating, String comment, LocalDate reviewDate) {
        this.user = user;
        this.property = property;
        setRating(rating); // Sử dụng setter
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
        if(rating<1 ||rating>5){
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }
}
