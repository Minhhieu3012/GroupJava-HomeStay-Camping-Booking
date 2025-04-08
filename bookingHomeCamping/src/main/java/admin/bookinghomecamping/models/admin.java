package admin.bookinghomecamping.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Entity
@Table (name = "admin")
public class admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(nullable = false)
    private String adminName;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 8)
    private String password;

    @Email
    @NotBlank
    @Column(nullable = false)
    private String email;

    public admin(String email, String adminName, String password) {
        this.email = email;
        this.adminName = adminName;
        this.password = password;
    }
    public admin() {
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getPassword() {
        return password;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
