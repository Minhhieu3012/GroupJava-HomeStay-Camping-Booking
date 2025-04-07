package ut.edu.database.dtos;

import java.time.LocalDate;

public class ReviewDTO {
    private Long id;
    private Long userId;
    private Long propertyId;
    private Byte rating;
    private String comment;
    private LocalDate reviewDate;

    public ReviewDTO() {}

    public ReviewDTO(Long id, Long userId, Long propertyId, Byte rating, String comment, LocalDate reviewDate) {
        this.id = id;
        this.userId = userId;
        this.propertyId = propertyId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    //getters setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Byte getRating() {
        return rating;
    }

    public void setRating(Byte rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }
}
