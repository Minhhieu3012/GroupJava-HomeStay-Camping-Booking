package admin.bookinghomecamping.dto.request;

import jakarta.validation.constraints.Size;

public class adminCreationRequest {
    private long id;
    @Size(min = 3, message = "Admin Name must be at least 8 characters")
    private String adminName;
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    private String email;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
