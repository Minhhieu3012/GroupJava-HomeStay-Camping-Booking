package ut.edu.database.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "Users")
public class User {
    @Id //khoa chinh
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Size(min = 8) //do dai toi thieu cua mk
    @Column(nullable = false)
    private String password;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; //vai tro (vd: customer, homestay owner, admin)

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Property> properties;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;


    //Constructors
    public User() {

    }
    public User(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password; //co the ma hoa bang BCrypt trc khi luu
        this.role = role;
    }

    //Getters
    public List<Property> getProperties() {
        return properties;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public Long getId() { //truy cap ma dinh danh user
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() { //tuy chon (co the co hoac khong)
        return password;
    }

    public Role getRole() {
        return role;
    }

    //Setters
    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    //define enum Role
    public enum Role {
        CUSTOMER,
        OWNER,
        ADMIN
    }
}
