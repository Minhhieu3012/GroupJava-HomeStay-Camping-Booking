package com.chotot.travel.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    private String email;
    private String password;
    private String role; // "customer" | "owner"


    private String name;    // tên người dùng
    private String phone;   // số điện thoại

    // Constructors
    public User() {}
    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
 ///  bổ sưng thêm dữ liệu mơius
    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}