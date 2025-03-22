package ut.edu.database.models;
import jakarta.persistence.*;
import javax.management.relation.Role;

@Entity
@Table(name = "User")
public class User {
    @Id //khoa chinh
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role; //vai tro (vd: customer, homestay owner, admin)

    //Constructors
    public User() {

    }
    public User(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    //Getters Setters
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
