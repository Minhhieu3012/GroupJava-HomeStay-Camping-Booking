package com.homestay.review.model;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating; // Đánh giá từ 1 đến 5 sao
    private String comment; // Bình luận của khách hàng

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date(); // Thời gian tạo đánh giá

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer; // Khách hàng đánh giá

    @ManyToOne
    @JoinColumn(name = "homestay_id", nullable = false)
    private Homestay homestay; // Homestay được đánh giá

    // Getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Homestay getHomestay() {
        return homestay;
    }

    public void setHomestay(Homestay homestay) {
        this.homestay = homestay;
    }
}